-module(measure).
-compile(export_all).

-define(MAX_INT, 16#7FFFFFFF).

%% Vector Operations
vec_equal(Vec1, Vec2) ->
    Vec1 =:= Vec2.

vec_add(Vec1, Vec2) ->
    case length(Vec1) =:= length(Vec2) of
        true ->
            lists:zipwith(fun(X, Y) -> X + Y end, Vec1, Vec2);
        false ->
            unequal_lengths
    end.

vec_dotproduct(Vec1, Vec2) ->
    case length(Vec1) =:= length(Vec2) of
        true ->
            lists:sum(lists:zipwith(fun(X, Y) -> X * Y end, Vec1, Vec2));
        false ->
            unequal_lengths
    end.

vec_div(Vec1, Vec2) ->
    case length(Vec1) =:= length(Vec2) andalso length(Vec2) =/= 0 andalso (not lists:member(0, Vec2)) of
        true ->
            vec_div_after_validation(lists:reverse(Vec1), lists:reverse(Vec2), [], 0);
        false ->
            case length(Vec2) =:= 0 of
                true ->
                    zero_divisor_vec;
                false ->
                    case lists:member(0, Vec2) of
                        true ->
                            zero_divisor;
                        false ->
                            unequal_lengths
                    end
            end
    end.

vec_div_after_validation([H_RevVec1], _Vec2, ResVec, Carry) ->
    [Carry + H_RevVec1|ResVec];
vec_div_after_validation([H_RevVec1|T_RevVec1], [H_Vec2|T_Vec2], ResVec, Carry) ->
    Dividend = H_RevVec1 + Carry,
    Remainder = Dividend rem H_Vec2,
    vec_div_after_validation(T_RevVec1, T_Vec2, [Remainder|ResVec], Dividend div H_Vec2).

vec_dotdiv(BaseValue, Vec) ->
    case length(Vec) =/= 0 andalso (not lists:member(0, Vec)) of
        true ->
            vec_dotdiv_after_validation(BaseValue, Vec, []);
        false ->
            case length(Vec) =:= 0 of
                true ->
                    zero_divisor_vec;
                false ->
                    case lists:member(0, Vec) of
                        true ->
                            zero_divisor;
                        false ->
                            unequal_lengths
                    end
            end
    end.

vec_dotdiv_after_validation(0, [], ResVec) ->
    lists:reverse(ResVec);
vec_dotdiv_after_validation(Value, [H_Vec|T_Vec], ResVec) ->
    vec_dotdiv_after_validation(Value rem H_Vec, T_Vec, [Value div H_Vec|ResVec]).

vec_scale(C, Vec) ->
    [C * I || I <- Vec].

%% Measurement System
ms_base_factors(StepFactors) ->
    ms_base_factors(StepFactors, []).

ms_base_factors([], BaseFactors) ->
    lists:reverse(BaseFactors);
ms_base_factors([_H_HSF|T_HSF], BaseFactors) ->
    ms_base_factors(T_HSF, [lists:foldl(fun(X, Y) -> X * Y end, 1, T_HSF)|BaseFactors]).

ms_base(QuantityVec, BaseFactors) ->
    vec_dotproduct(QuantityVec, BaseFactors).

ms_normalize(QuantityVec, StepFactors) ->
    vec_div(QuantityVec, StepFactors).

ms_equal(QuantityVec1, QuantityVec2, StepFactors) ->
    vec_equal(ms_normalize(QuantityVec1, StepFactors), ms_normalize(QuantityVec2, StepFactors)).

ms_add(QuantityVec1, QuantityVec2, StepFactors) ->
    ms_normalize(vec_add(QuantityVec1, QuantityVec2), StepFactors).

ms_scale(C, QuantityVec, StepFactors) ->
    ms_normalize(vec_scale(C, QuantityVec), StepFactors).

%% Measurement System UI
ms_ui_parse_unitconversiondesc(Desc) ->
    Tokens = string:tokens(string:strip(Desc), " +"),
    ms_ui_parse_unitconversiondesc(Tokens, [?MAX_INT], []).

ms_ui_parse_unitconversiondesc([BaseUnit], StepFactors, SysUnits) ->
    {lists:reverse(StepFactors), lists:reverse([BaseUnit|SysUnits])};
ms_ui_parse_unitconversiondesc([H1_Tokens, H2_Tokens|T_Tokens], StepFactors, SysUnits) ->
    ms_ui_parse_unitconversiondesc(T_Tokens, [list_to_integer(H2_Tokens)|StepFactors], [H1_Tokens|SysUnits]).

ms_ui_parse_quantity(Quantity, SysUnits) ->
    Tokens = string:tokens(string:strip(Quantity), " +"),
    QuantityUnitAndValues = ms_ui_parse_quantity_unit_and_values(Tokens, []),
    ms_ui_quantity_vec(QuantityUnitAndValues, SysUnits).

ms_ui_parse_quantity_unit_and_values([], QuantityUnitAndValues) ->
    lists:reverse(QuantityUnitAndValues);
ms_ui_parse_quantity_unit_and_values([H1_Tokens, H2_Tokens|T_Tokens], QuantityUnitAndValues) ->
    ms_ui_parse_quantity_unit_and_values(T_Tokens, [{H2_Tokens, list_to_integer(H1_Tokens)}|QuantityUnitAndValues]).

ms_ui_quantity_vec(QuantityUnitAndValues, SysUnits) ->
    [begin
        case lists:keyfind(SysUnit, 1, QuantityUnitAndValues) of
            {_QuantityUnit, QuantityValue} ->
                QuantityValue;
            false ->
                0
        end
     end || SysUnit <- SysUnits].

ms_ui_equal(Quantity1, Quantity2, SysUnits, StepFactors) ->
    ms_equal(ms_ui_parse_quantity(Quantity1, SysUnits), 
             ms_ui_parse_quantity(Quantity2, SysUnits), StepFactors).

ms_ui_format(QuantityVec, SysUnits) ->
    ms_ui_format(QuantityVec, SysUnits, "").

ms_ui_format([], [], [_|Res]) ->
    Res;
ms_ui_format([H_QuantityVec|T_QuantityVec]=QuantityVec, [H_SysUnits|T_SysUnits], Res) ->
    case H_QuantityVec =/= 0 orelse length(QuantityVec) =:= 1 of
        true ->
            ValueAndUnit = string:join([integer_to_list(H_QuantityVec), H_SysUnits], " "),
            NewRes = string:join([Res, ValueAndUnit], " "),
            ms_ui_format(T_QuantityVec, T_SysUnits, NewRes);
        false ->
            ms_ui_format(T_QuantityVec, T_SysUnits, Res)
    end.

ms_ui_add(Quantity1, Quantity2, SysUnits, StepFactors) ->
    ms_ui_format(ms_add(ms_ui_parse_quantity(Quantity1, SysUnits), 
                        ms_ui_parse_quantity(Quantity2, SysUnits), StepFactors), SysUnits).

ms_ui_baseformat(QuantityVec, SysUnits, StepFactors) ->
    BaseFactors = ms_base_factors(StepFactors),
    BasedValue = ms_base(QuantityVec, BaseFactors),
    string:join([integer_to_list(BasedValue), lists:last(SysUnits)], " ").

ms_ui_scale(C, Quantity, SysUnits, StepFactors) ->
    ms_ui_format(ms_scale(list_to_integer(C), ms_ui_parse_quantity(Quantity, SysUnits), StepFactors), SysUnits).

%% Tests
% Vector Operation Tests
test_vec_equal() ->
    true = vec_equal([1, 2, 3], [1, 2, 3]),
    true = vec_equal([], []),
    false = vec_equal([1, 2], [1, 2, 3]),
    false = vec_equal([1, 3, 4], [1, 2, 3]),

    test_vec_equal_ok.

test_vec_add() ->
    [5, 7, 9] = vec_add([1, 2, 3], [4, 5, 6]),
    [] = vec_add([], []),
    unequal_lengths = vec_add([1, 2], [4, 5, 6]),

    test_vec_add_ok.

test_vec_dotproduct() ->
    102 = vec_dotproduct([1, 0, 2], [100, 10, 1]),
    0 = vec_dotproduct([], []),
    unequal_lengths = vec_dotproduct([1, 2], [4, 5, 6]),

    test_vec_dotproduct_ok.

test_vec_div() ->
    [0, 0, 2, 0] = vec_div([0, 0, 0, 24], [?MAX_INT, 1760, 3, 12]),
    [2, 6, 0, 4] = vec_div([1, 1765, 0, 40], [?MAX_INT, 1760, 3, 12]),
    unequal_lengths = vec_div([1, 2, 3], [1, 2]),
    zero_divisor_vec = vec_div([], []),
    zero_divisor = vec_div([1, 2], [0, 4]),

    test_vec_div_ok.

test_vec_dotdiv() ->
    [1, 0, 2] = vec_dotdiv(102, [100, 10, 1]),
    zero_divisor_vec = vec_dotdiv(0, []),
    zero_divisor = vec_dotdiv(0, [0, 4]),

    test_vec_dotdiv_ok.

test_vec_scale() ->
    [2, 4, 6] = vec_scale(2, [1, 2, 3]),
    [0, 0] = vec_scale(0, [1, 2]),
    [] = vec_scale(2, []),

    test_vec_scale_ok.

% Measurement System Tests
test_ms_base_factors() ->
    [1760 * 3 * 12, 3 * 12, 12, 1] = ms_base_factors([?MAX_INT, 1760, 3, 12]),

    test_ms_base_factors_ok.

test_ms_base() ->
    BaseFactors = [1760 * 3 * 12, 3 * 12, 12, 1],
    63472 = ms_base([1, 2, 3, 4], BaseFactors),
    63396 = ms_base([1, 0, 3, 0], BaseFactors),

    test_ms_base_ok.

test_ms_normalize() ->
    StepFactors = [?MAX_INT, 1760, 3, 12],
    [1, 4, 0, 1] = ms_normalize([0, 1762, 5, 13], StepFactors),

    test_ms_normalize_ok.

test_ms_equal() ->
    StepFactors = [?MAX_INT, 1760, 3, 12],
    true = ms_equal([1, 2, 3, 4], [0, 0, 0, 63472], StepFactors),
    true = ms_equal([0, 1765, 0, 40], [1, 6, 0, 4], StepFactors),
    false = ms_equal([0, 1765, 0, 41], [1, 6, 0, 4], StepFactors),

    test_ms_equal_ok.

test_ms_add() ->
    StepFactors = [?MAX_INT, 1760, 3, 12],
    [0, 0, 2, 0] = ms_add([0, 0, 0, 13], [0, 0, 0, 11], StepFactors),
    [0, 3, 0, 0] = ms_add([0, 0, 3, 0], [0, 2, 0, 0], StepFactors),

    test_ms_add_ok.

test_ms_scale() ->
    StepFactors = [?MAX_INT, 1760, 3, 12],

    [0, 6, 0, 0] = ms_scale(2, [0, 2, 3, 0], StepFactors),
    [0, 6, 1, 1] = ms_add(ms_scale(2, [0, 2, 3, 0], StepFactors), [0, 0, 0, 13], StepFactors),

    test_ms_scale_ok.

% Measurement System UI Tests
test_ms_ui_parse_unitconversiondesc() ->
    StepFactors = [?MAX_INT, 1760, 3, 12],
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    {StepFactors, SysUnits} = ms_ui_parse_unitconversiondesc(" Mile  1760 Yard 3 Feet 12 Inch  ").

test_ms_ui_parse_quantity() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    [1, 2, 3, 4] = ms_ui_parse_quantity(" 1 Mile  2 Yard 3 Feet 4 Inch  ", SysUnits),
    [1, 2, 0, 4] = ms_ui_parse_quantity("1 Mile 4 Inch 2 Yard", SysUnits),
    [1, 2, 0, 4] = ms_ui_parse_quantity("1 Mile 2 Yard 4 Inch", SysUnits),
    [0, 0, 3, 0] = ms_ui_parse_quantity("3 Feet", SysUnits),
    
    test_ms_ui_parse_quantity_ok.

test_ms_ui_equal() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    StepFactors = [?MAX_INT, 1760, 3, 12],
    true = ms_ui_equal("1 Mile 2 Yard 3 Feet 4 Inch", "63472 Inch", SysUnits, StepFactors),
    true = ms_ui_equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch", SysUnits, StepFactors),
    false = ms_ui_equal("1765 Yard 41 Inch", "1 Mile 6 Yard 4 Inch", SysUnits, StepFactors),

    test_ms_ui_equal_ok.

test_ms_ui_format() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    "1 Mile 3 Yard 4 Inch" = ms_ui_format([1, 3, 0, 4], SysUnits),
    "2 Feet 0 Inch" = ms_ui_format([0, 0, 2, 0], SysUnits),

    test_ms_ui_format_ok.

test_ms_ui_add() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    StepFactors = [?MAX_INT, 1760, 3, 12],
    "2 Feet 0 Inch" = ms_ui_add("13 Inch", "11 Inch", SysUnits, StepFactors),
    "3 Yard 0 Inch" = ms_ui_add("3 Feet", "2 Yard", SysUnits, StepFactors),

    test_ms_ui_add_ok.

test_ms_ui_baseFormat() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    StepFactors = [?MAX_INT, 1760, 3, 12],
    "63472 Inch" = ms_ui_baseformat([1, 3, 0, 4], SysUnits, StepFactors),
    "2 Feet 0 Inch" = ms_ui_add("13 Inch", "11 Inch", SysUnits, StepFactors),
    
    test_ms_ui_baseFormat_ok.

test_ms_ui_scale() ->
    SysUnits = ["Mile", "Yard", "Feet", "Inch"],
    StepFactors = [?MAX_INT, 1760, 3, 12],
    "6 Yard 0 Inch" = ms_ui_scale("2", "2 Yard 3 Feet", SysUnits, StepFactors),
    "6 Yard 1 Feet 1 Inch" = ms_ui_add(ms_ui_scale("2", "2 Yard 3 Feet", SysUnits, StepFactors), 
                                       "13 Inch", SysUnits, StepFactors),

    test_ms_ui_scale_ok.

test() ->
    test_vec_equal(),
    test_vec_add(),
    test_vec_dotproduct(),
    test_vec_div(),
    test_vec_dotdiv(),
    test_vec_scale(),

    test_ms_base_factors(),
    test_ms_base(),
    test_ms_normalize(),
    test_ms_equal(),
    test_ms_add(),
    test_ms_scale(),

    test_ms_ui_parse_unitconversiondesc(),
    test_ms_ui_parse_quantity(),
    test_ms_ui_format(),
    test_ms_ui_add(),
    test_ms_ui_baseFormat(),
    test_ms_ui_scale(),

    test_ok.

main() ->
    {StepFactors, SysUnits} = ms_ui_parse_unitconversiondesc("Mile 1760 Yard 3 Feet 12 Inch"),
    R1 = ms_ui_equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch", SysUnits, StepFactors),
    io:format("1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? ~p~n", [R1]),
    R2 = ms_ui_add("13 Inch", "11 Inch", SysUnits, StepFactors),
    io:format("13 Inch + 11 Inch = ~p~n", [R2]),
    R3 = ms_ui_scale("2", "2 Yard 3 Feet", SysUnits, StepFactors),
    io:format("2 * 2 Yard 3 Feet = ~p~n", [R3]),
    R4 = ms_ui_add(ms_ui_scale("2", "2 Yard 3 Feet", SysUnits, StepFactors), "13 Inch", SysUnits, StepFactors),
    io:format("2 * 2 Yard 3 Feet + 13 Inch = ~p~n", [R4]),

    {StepFactors2, SysUnits2} = ms_ui_parse_unitconversiondesc("OZ 2 TBSP 3 TSP"),
    R5 = ms_ui_equal("1 OZ 10 TSP", "2 OZ 1 TBSP 1 TSP", SysUnits2, StepFactors2),
    io:format("1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? ~p~n", [R5]),
    R6 = ms_ui_add("1 OZ", "3 TBSP 3 TSP", SysUnits2, StepFactors2),
    io:format("1 OZ + 3 TBSP 3 TSP = ~p~n", [R6]),
    R7 = ms_ui_scale("3", "1 OZ 10 TSP", SysUnits2, StepFactors2),
    io:format("3 * 1 OZ 10 TSP = ~p~n", [R7]),
    R8 = ms_ui_add(ms_ui_scale("3", "1 OZ 10 TSP", SysUnits2, StepFactors2), "3 TBSP 1 TSP", SysUnits2, StepFactors2),
    io:format("3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = ~p~n", [R8]),

    {StepFactors3, SysUnits3} = ms_ui_parse_unitconversiondesc("Day 24 Hour 60 Minute 60 Second"),
    R9 = ms_ui_equal("7 Hour 59 Minute 60 Second", "8 Hour", SysUnits3, StepFactors3),
    io:format("7 Hour 59 Minute 60 Second == 8 Hour ? ~p~n", [R9]),
    R10 = ms_ui_add("1 Day 23 Hour 59 Minute 59 Second", "1 Second", SysUnits3, StepFactors3),
    io:format("1 Day 23 Hour 59 Minute 59 Second + 1 Second = ~p~n", [R10]),
    R11 = ms_ui_scale("2", "11 Hour 22 Minute 33 Second", SysUnits3, StepFactors3),
    io:format("2 * 11 Hour 22 Minute 33 Second = ~p~n", [R11]),
    R12 = ms_ui_add(ms_ui_scale("2", "11 Hour 22 Minute 33 Second", SysUnits3, StepFactors3), "60 Second", SysUnits3, StepFactors3),
    io:format("2 * 11 Hour 22 Minute 33 Second + 60 Second = ~p~n", [R12]),

    {StepFactors4, SysUnits4} = ms_ui_parse_unitconversiondesc("Mile 4 Miya 440 Yard 3 Feet 12 Inch"),
    R13 = ms_ui_equal("4 Miya 446 Yard 40 Inch", "1 Mile 1 Miya 7 Yard 4 Inch", SysUnits4, StepFactors4),
    io:format("4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? ~p~n", [R13]),
    R14 = ms_ui_add("3 Miya 13 Inch", "445 Yard 11 Inch", SysUnits4, StepFactors4),
    io:format("3 Miya 13 Inch + 445 Yard 11 Inch = ~p~n", [R14]),
    R15 = ms_ui_add(ms_ui_scale("2", "3 Miya 13 Inch", SysUnits4, StepFactors4), "445 Yard 11 Inch", SysUnits4, StepFactors4),
    io:format("2 * 3 Miya 13 Inch + 445 Yard 11 Inch = ~p~n", [R15]).
