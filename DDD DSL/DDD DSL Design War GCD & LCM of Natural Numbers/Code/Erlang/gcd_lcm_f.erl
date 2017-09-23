-module(gcd_lcm_f).
-compile(export_all).

%% gcd lcm
gcd(Ns) ->
    unified_proc(gcd, Ns).

lcm(Ns) ->
    unified_proc(lcm, Ns).

%% Specification
spec() -> 
    [
        {gcd, [{extraction_type_pf, fun extract_common_prime_factors/1}, 
               {extraction_type_power, fun extract_min_power/1}]},
        {lcm, [{extraction_type_pf, fun extract_allnp_prime_factors/1}, 
               {extraction_type_power, fun extract_max_power/1}]}
    ].

%% Unified Process
unified_proc(SolutionType, Ns) ->
    {ExtractPfFunc, ExtractPowerFunc} = extract_spec_funcs(SolutionType, spec()),
    {ListOfPfs, ListOfPfPs} = prime_factorize_list(Ns),
    Pfs = extract_prime_factors(ExtractPfFunc, ListOfPfs),
    PfPs = extract_powers(ExtractPowerFunc, Pfs, ListOfPfPs),
    product_powers(PfPs).

% Extract Specification Items
extract_spec_funcs(SolutionType, Spec) ->
    {_, SpecItems} = lists:keyfind(SolutionType, 1, Spec),
    {_, ExtractPfFunc} = lists:keyfind(extraction_type_pf, 1, SpecItems),
    {_, ExtractPowerFunc} = lists:keyfind(extraction_type_power, 1, SpecItems),
    {ExtractPfFunc, ExtractPowerFunc}.

% Prime Factorize
prime_factorize_list(Ns) ->
    ListOfPfPs = [prime_factorize(N) || N <- Ns],
    ListOfPfsAndPs = [lists:unzip(PfPs) || PfPs <- ListOfPfPs],
    {ListOfPfs, _} = lists:unzip(ListOfPfsAndPs),
    {ListOfPfs, ListOfPfPs}.

prime_factorize(N) ->
    prime_factorize(N, 2, []).

prime_factorize(1, _C, PfPs) ->
    lists:reverse(PfPs);
prime_factorize(N, C, PfPs) ->
    case is_prime(C) andalso is_factor(C, N) of
        true ->
            NewPfPs = acc_prime_factors(C, PfPs),
            prime_factorize(N div C, 2, NewPfPs);
        false ->
            prime_factorize(N, C + 1, PfPs)
    end.

acc_prime_factors(Pf, PfPs) ->
    case lists:keyfind(Pf, 1, PfPs) of
        false ->
            [{Pf, 1} | PfPs];
        {Pf, P} ->
            lists:keyreplace(Pf, 1, PfPs, {Pf, P + 1})
    end.

% Extract Prime Factors According to the Specification
extract_prime_factors(Func, ListOfPfs) ->
    Func(ListOfPfs).

extract_common_prime_factors(ListOfPfs) ->
    intersect(ListOfPfs).

extract_allnp_prime_factors(ListOfPfs) ->
    union(ListOfPfs).

% Extract Powers of the Extracted Prime Factors According to the Specification
extract_powers(Func, Pfs, ListOfPfPs) ->
    [{Pf, extract_power(Func, extract_pf_powers(Pf, ListOfPfPs))} || Pf <- Pfs].

extract_pf_powers(Pf, ListOfPfPs) ->
    [extract_pf_power(Pf, PfPs) || PfPs <- ListOfPfPs].

extract_pf_power(Pf, PfPs) ->
    case lists:keyfind(Pf, 1, PfPs) of
            {Pf, P} -> P;
            false -> 0
    end.

extract_power(Func, Ps) ->
    Func(Ps).

extract_min_power(Ps) ->
    lists:min(Ps).

extract_max_power(Ps) ->
    lists:max(Ps).

% Product of Power Results
product_powers(PfPs) ->
    lists:foldl(fun({Pf, P}, Acc) -> pow(Pf, P) * Acc end, 1, PfPs).

%% Math Operations
is_prime(N) -> 
    lists:all(fun(I) -> N rem I =/= 0 end, lists:seq(2, N - 1)).

is_factor(N1, N2) ->
    N2 rem N1 =:= 0.

pow(_N, 0) ->
    1;
pow(N, P) ->
    lists:foldl(fun(_I, Acc) -> N * Acc end, 1, lists:seq(1, P)).

%% Set Operations
intersect(Ss) ->
    if
        Ss =:= [] -> 
            [];
        length(Ss) =:= 1 ->
            [];
        true ->
            [H|T] = Ss,
            lists:foldl(fun(S, Acc) -> intersect(S, Acc) end, H, T)
    end.

union(Ss) ->
    if
        Ss =:= [] -> 
            [];
        length(Ss) =:= 1 ->
            [H|_T] = Ss,
            H;
        true ->
            [H|T] = Ss,
            lists:foldl(fun(S, Acc) -> union(S, Acc) end, H, T)
    end.

intersect([], _S2) ->
    [];
intersect([H1|T1], S2) ->
    case lists:member(H1, S2) of
        true ->
            [H1|intersect(T1, S2)];
        false ->
            intersect(T1, S2)
    end.

union([], S2) ->
    lists:sort(lists:reverse(S2));
union([H1|T1], S2) ->
    case lists:member(H1, S2) of
        false ->
            union(T1, [H1|S2]);
        true ->
            union(T1, S2)
    end.

%% Tests
% Math Operation Tests
test_is_prime() ->
    true = is_prime(2),
    true = is_prime(3),
    false = is_prime(4),
    true = is_prime(5),
    test_is_prime_ok.

test_is_factor() ->
    true = is_factor(1, 3),
    false = is_factor(2, 3),
    true = is_factor(3, 3),
    true = is_factor(2, 6),
    
    test_is_factor_ok.

test_pow() ->
    1 = pow(1, 0),
    8 = pow(2, 3),

    test_pow_ok.

% Set Operation Tests
test_intersect() ->
    [] = intersect([], []),
    [] = intersect([1], []),
    [] = intersect([], [1]),
    [2, 3] = intersect([1, 2, 3, 5], [2, 3, 4]),
    [] = intersect([1, 2, 3], [4, 5]),

    [] = intersect([]),
    [] = intersect([[1]]),
    [2, 3] = intersect([[1, 2, 3, 5], [2, 3, 4]]),
    [] = intersect([[1, 2, 3], [4, 5]]),
    [3, 4] = intersect([[1, 2, 3, 4], [3, 4, 5], [3, 4, 5, 6]]),

    test_intersect_ok.

test_union() ->
    [] = union([], []),
    [1] = union([1], []),
    [1] = union([], [1]),
    [1, 2, 3, 4] = union([1, 3], [2, 4]),
    [1, 2, 3, 4] = union([1, 2, 3], [3, 4]),
    [1, 2, 3, 4, 5] = union([1, 2, 3], [2, 3, 4, 5]),

    [] = union([]),
    [1] = union([[1]]),
    [1, 2, 3, 4] = union([[1, 3], [2, 4]]),
    [1, 2, 3, 4] = union([[1, 2, 3], [3, 4]]),
    [1, 2, 3, 4, 5] = union([[1, 2, 3], [2, 3, 4, 5]]),
    [1, 2, 3, 4, 5, 6] = union([[1, 2, 3], [3, 4], [4, 5, 6]]),

    test_union_ok.

% GCD and LCM Tests
test_prime_factorize() ->
    [{2, 1}] = prime_factorize(2),
    [{2, 1}, {3, 1}] = prime_factorize(6),

    [{2, 1}, {3, 2}, {5, 1}] = prime_factorize(90),
    [{2, 2}, {3, 1}, {5, 1}, {7, 1}] = prime_factorize(420),
    [{2, 1}, {3, 3}, {5, 2}, {7, 1}] = prime_factorize(9450),

    test_prime_factorize_ok.

test_prime_factorize_list() ->
    {[[2, 3, 5], [2, 3, 5, 7], [2, 3, 5, 7]],
                            
     [[{2, 1}, {3, 2}, {5, 1}], 
      [{2, 2}, {3, 1}, {5, 1}, {7, 1}], 
      [{2, 1}, {3, 3}, {5, 2}, {7, 1}]]
    } = prime_factorize_list([90, 420, 9450]),

    test_prime_factorize_list_ok.

test_extract_prime_factors() ->
    [2, 3, 5] = extract_prime_factors(fun extract_common_prime_factors/1, 
                                      [[2, 3, 5], [2, 3, 5, 7], [2, 3, 5, 7]]),
    [2, 3, 5, 7] = extract_prime_factors(fun extract_allnp_prime_factors/1, 
                                         [[2, 3, 5], [2, 3, 5, 7], [2, 3, 5, 7]]),

    test_extract_prime_factors_ok.

test_extract_powers() ->
    Pfs = [2, 3, 5],
    ListOfPfPs = [[{2, 1}, {3, 2}, {5, 1}], 
                  [{2, 2}, {3, 1}, {5, 1}, {7, 1}], 
                  [{2, 1}, {3, 3}, {5, 2}, {7, 1}]],
    [{2, 1}, {3, 1}, {5, 1}] = extract_powers(fun extract_min_power/1, Pfs, ListOfPfPs),
    [{2, 2}, {3, 3}, {5, 2}] = extract_powers(fun extract_max_power/1, Pfs, ListOfPfPs),

    test_extract_powers_ok.

test_product_powers() ->
    30 = product_powers([{2, 1}, {3, 1}, {5, 1}]),
    18900 = product_powers([{2, 2}, {3, 3}, {5, 2}, {7, 1}]),

    test_product_powers_ok.

test_gcd() ->
    30 = gcd([90, 420, 9450]),

    test_gcd_ok.

test_lcm() ->
    18900 = lcm([90, 420, 9450]),

    test_lcm_ok.

test() ->
    test_is_prime(),
    test_is_factor(),
    test_pow(),

    test_intersect(),
    test_union(),

    test_prime_factorize(),
    test_prime_factorize_list(),
    test_extract_prime_factors(),
    test_extract_powers(),
    test_product_powers(),    

    test_gcd(),
    test_lcm(),

    test_ok.