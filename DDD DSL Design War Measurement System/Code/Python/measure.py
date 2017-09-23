## Vector Operations
def vec_equal(vec1, vec2):
    return vec1 == vec2

def vec_add(vec1, vec2):
    if len(vec1) == len(vec2):
        return [c1 + c2 for (c1, c2) in zip(vec1, vec2)]
    else:
        return None

def vec_dotproduct(vec1, vec2):
    if len(vec1) == len(vec2):
        return sum([c1 * c2 for (c1, c2) in zip(vec1, vec2)])
    else:
        return None

def vec_div(vec1, vec2):
    if len(vec1) == len(vec2) and len(vec2) != 0 and (0 not in vec2):
        return vec_div_after_validation(vec1[::-1], vec2[::-1])
    else:
        return None

def vec_div_after_validation(rvec1, rvec2):
    result = []
    carry = 0
    for (c1, c2) in zip(rvec1, rvec2):
        dividend = c1 + carry
        divisor = c2
        remainder = dividend % divisor
        result.append(remainder)
        carry = dividend / divisor
    return result[::-1]

def vec_dotdiv(baseValue, vec):
    if len(vec) != 0 and (0 not in vec):
        return vec_dotdiv_after_validation(baseValue, vec)
    else:
        return None

def vec_dotdiv_after_validation(baseValue, vec):
    result = []
    remainder = baseValue
    for c in vec:
        quotient = remainder / c
        remainder = remainder % c
        result.append(quotient)
    return result

def vec_scale(c, vec):
    return [c * vi for vi in vec]

## Measurement System
def ms_base_factors(stepFactors):
    result = []
    for (i, stepFactor) in enumerate(stepFactors[1:]):
        result.append(reduce(lambda x, y: x * y, stepFactors[i + 1:]))
    result.append(1)
    return result

def ms_base(quantityVec, baseFactors):
    return vec_dotproduct(quantityVec, baseFactors)

def ms_normalize(quantityVec, stepFactors):
    return vec_div(quantityVec, stepFactors)

def ms_equal(quantityVec1, quantityVec2, stepFactors):
    return vec_equal(ms_normalize(quantityVec1, stepFactors), ms_normalize(quantityVec2, stepFactors))

def ms_add(quantityVec1, quantityVec2, stepFactors):
    return ms_normalize(vec_add(quantityVec1, quantityVec2), stepFactors)

def ms_scale(c, quantityVec, stepFactors):
    return ms_normalize(vec_scale(c, quantityVec), stepFactors)

## Measurement System UI
import re
import string

def ms_ui_parse_unitconversiondesc(desc):
    tokens = re.split(' +', string.strip(desc))
    stepFactors = [MAX_INT()]
    sysUnits = [tokens[0]]
    for i in range(1, len(tokens), 2):
        stepFactors.append(int(tokens[i]))
        sysUnits.append(tokens[i + 1])
    return (stepFactors, sysUnits)

def ms_ui_parse_quantity(quantity, sysUnits):
    (values, units) = ms_ui_parse_values_and_units(quantity)
    return [ms_ui_quantity_value(values, units, sysUnit) for sysUnit in sysUnits]

def ms_ui_parse_values_and_units(quantity):
    tokens = re.split(' +', string.strip(quantity))
    values = []
    units = []
    for i in range(0, len(tokens), 2):
        values.append(int(tokens[i]))
        units.append(tokens[i + 1])
    return (values, units)

def ms_ui_quantity_value(values, units, sysUnit):
    if sysUnit in units:
        return values[units.index(sysUnit)]
    return 0        

def ms_ui_equal(quantity1, quantity2, sysUnits, stepFactors):
    return ms_equal(ms_ui_parse_quantity(quantity1, sysUnits), 
                    ms_ui_parse_quantity(quantity2, sysUnits), stepFactors)

def ms_ui_format(quantityVec, sysUnits):
    result = ''
    for i in xrange(0, len(quantityVec)):
        if quantityVec[i] != 0 or i == len(quantityVec) - 1:
            valueAndUnit = ' '.join([str(quantityVec[i]), sysUnits[i]])
            result = ' '.join([result, valueAndUnit])
    return result[1:]

def ms_ui_add(quantity1, quantity2, sysUnits, stepFactors):
    return ms_ui_format(ms_add(ms_ui_parse_quantity(quantity1, sysUnits), 
                               ms_ui_parse_quantity(quantity2, sysUnits), stepFactors), sysUnits)

def ms_ui_baseformat(quantityVec, sysUnits, stepFactors):
    baseFactors = ms_base_factors(stepFactors)
    basedValue = ms_base(quantityVec, baseFactors)
    return (' '.join([str(basedValue), sysUnits[-1]]))

def ms_ui_scale(c, quantity, sysUnits, stepFactors):
    return ms_ui_format(ms_scale(int(c), ms_ui_parse_quantity(quantity, sysUnits), stepFactors), sysUnits)

def MAX_INT():
    return 0x7FFFFFFF

## Tests
# Vector Operation Tests
def test_vec_equal():
    assert vec_equal([1, 2, 3], [1, 2, 3])
    assert vec_equal([], [])
    assert not vec_equal([1, 2], [1, 2, 3])
    assert not vec_equal([1, 3, 4], [1, 2, 3])

    print 'test_vec_equal ok'

def test_vec_add():
    assert [5, 7, 9] == vec_add([1, 2, 3], [4, 5, 6])
    assert [] == vec_add([], [])
    assert None == vec_add([1, 2], [4, 5, 6])

    print 'test_vec_add ok'

def test_vec_dotproduct():
    assert 102 == vec_dotproduct([1, 0, 2], [100, 10, 1])
    assert 0 == vec_dotproduct([], [])
    assert None == vec_dotproduct([1, 2], [4, 5, 6])

    print 'test_vec_dotproduct ok'

def test_vec_div():
    assert [0, 0, 2, 0] == vec_div([0, 0, 0, 24], [MAX_INT(), 1760, 3, 12])
    assert [2, 6, 0, 4] == vec_div([1, 1765, 0, 40], [MAX_INT(), 1760, 3, 12])
    assert None == vec_div([1, 2, 3], [1, 2])
    assert None == vec_div([], [])
    assert None == vec_div([1, 2], [0, 4])

    print 'test_vec_div ok'

def test_vec_dotdiv():
    assert [1, 0, 2] == vec_dotdiv(102, [100, 10, 1])
    assert None == vec_dotdiv(0, [])
    assert None == vec_dotdiv(0, [0, 4])

    print 'test_vec_dotdiv ok'

def test_vec_scale():
    assert [2, 4, 6] == vec_scale(2, [1, 2, 3])
    assert [0, 0] == vec_scale(0, [1, 2])
    assert [] == vec_scale(2, [])

    print 'test_vec_scale ok'

# Measurement System Tests
def test_ms_base_factors():
    assert [1760 * 3 * 12, 3 * 12, 12, 1] == ms_base_factors([MAX_INT(), 1760, 3, 12])

    print 'test_ms_base_factors ok'

def test_ms_base():
    baseFactors = [1760 * 3 * 12, 3 * 12, 12, 1]
    assert 63472 == ms_base([1, 2, 3, 4], baseFactors)
    assert 63396 == ms_base([1, 0, 3, 0], baseFactors)

    print 'test_ms_base ok'

def test_ms_normalize():
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert [1, 4, 0, 1] == ms_normalize([0, 1762, 5, 13], stepFactors)

    print 'test_ms_normalize ok'

def test_ms_equal():
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert ms_equal([1, 2, 3, 4], [0, 0, 0, 63472], stepFactors)
    assert ms_equal([0, 1765, 0, 40], [1, 6, 0, 4], stepFactors)
    assert not ms_equal([0, 1765, 0, 41], [1, 6, 0, 4], stepFactors)

    print 'test_ms_equal ok'

def test_ms_add():
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert [0, 0, 2, 0] == ms_add([0, 0, 0, 13], [0, 0, 0, 11], stepFactors)
    assert [0, 3, 0, 0] == ms_add([0, 0, 3, 0], [0, 2, 0, 0], stepFactors)

    print 'test_ms_add ok'

def test_ms_scale():
    stepFactors = [MAX_INT(), 1760, 3, 12]

    assert [0, 6, 0, 0] == ms_scale(2, [0, 2, 3, 0], stepFactors)
    assert [0, 6, 1, 1] == ms_add(ms_scale(2, [0, 2, 3, 0], stepFactors), [0, 0, 0, 13], stepFactors)

    print 'test_ms_scale ok'

# Measurement System UI Tests
def test_ms_ui_parse_unitconversiondesc():
    stepFactors = [MAX_INT(), 1760, 3, 12]
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    assert (stepFactors, sysUnits) == ms_ui_parse_unitconversiondesc(' Mile  1760 Yard 3 Feet 12 Inch  ')

    print 'test_ms_ui_parse_unitconversiondesc ok'

def test_ms_ui_parse_quantity():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    assert [1, 2, 3, 4] == ms_ui_parse_quantity(' 1 Mile  2 Yard 3 Feet 4 Inch  ', sysUnits)
    assert [1, 2, 0, 4] == ms_ui_parse_quantity('1 Mile 4 Inch 2 Yard', sysUnits)
    assert [1, 2, 0, 4] == ms_ui_parse_quantity('1 Mile 2 Yard 4 Inch', sysUnits)
    assert [0, 0, 3, 0] == ms_ui_parse_quantity('3 Feet', sysUnits)
    
    print 'test_ms_ui_parse_quantity ok'

def test_ms_ui_equal():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert ms_ui_equal('1 Mile 2 Yard 3 Feet 4 Inch', '63472 Inch', sysUnits, stepFactors)
    assert ms_ui_equal('1765 Yard 40 Inch', '1 Mile 6 Yard 4 Inch', sysUnits, stepFactors)
    assert not ms_ui_equal('1765 Yard 41 Inch', '1 Mile 6 Yard 4 Inch', sysUnits, stepFactors)

    print 'test_ms_ui_equal ok'

def test_ms_ui_format():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    assert '1 Mile 3 Yard 4 Inch' == ms_ui_format([1, 3, 0, 4], sysUnits)
    assert '2 Feet 0 Inch' == ms_ui_format([0, 0, 2, 0], sysUnits)

    print 'test_ms_ui_format ok'

def test_ms_ui_add():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert '2 Feet 0 Inch' == ms_ui_add('13 Inch', '11 Inch', sysUnits, stepFactors)
    assert '3 Yard 0 Inch' == ms_ui_add('3 Feet', '2 Yard', sysUnits, stepFactors)

    print 'test_ms_ui_add ok'

def test_ms_ui_baseFormat():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert '63472 Inch' == ms_ui_baseformat([1, 3, 0, 4], sysUnits, stepFactors)
    assert '2 Feet 0 Inch' == ms_ui_add('13 Inch', '11 Inch', sysUnits, stepFactors)
    
    print 'test_ms_ui_baseFormat ok'

def test_ms_ui_scale():
    sysUnits = ['Mile', 'Yard', 'Feet', 'Inch']
    stepFactors = [MAX_INT(), 1760, 3, 12]
    assert "6 Yard 0 Inch" == ms_ui_scale('2', '2 Yard 3 Feet', sysUnits, stepFactors)
    assert '6 Yard 1 Feet 1 Inch' == ms_ui_add(ms_ui_scale('2', '2 Yard 3 Feet', sysUnits, stepFactors), 
                                               '13 Inch', sysUnits, stepFactors)

    print 'test_ms_ui_scale ok'

def test():
    test_vec_equal()
    test_vec_add()
    test_vec_dotproduct()
    test_vec_div()
    test_vec_dotdiv()
    test_vec_scale()

    test_ms_base_factors()
    test_ms_base()
    test_ms_normalize()
    test_ms_equal()
    test_ms_add()
    test_ms_scale()

    test_ms_ui_parse_unitconversiondesc()
    test_ms_ui_parse_quantity()
    test_ms_ui_equal()
    test_ms_ui_format()
    test_ms_ui_add()
    test_ms_ui_baseFormat()
    test_ms_ui_scale()

    print 'test ok'

def main():
    (stepFactors, sysUnits) = ms_ui_parse_unitconversiondesc('Mile 1760 Yard 3 Feet 12 Inch')
    r1 = ms_ui_equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch", sysUnits, stepFactors),
    print '1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? %s' % (r1)
    r2 = ms_ui_add('13 Inch', '11 Inch', sysUnits, stepFactors)
    print '13 Inch + 11 Inch = %s' % (r2)
    r3 = ms_ui_scale('2', '2 Yard 3 Feet', sysUnits, stepFactors)
    print '2 * 2 Yard 3 Feet = %s' % (r3)
    r4 = ms_ui_add(ms_ui_scale('2', '2 Yard 3 Feet', sysUnits, stepFactors), '13 Inch', sysUnits, stepFactors)
    print '2 * 2 Yard 3 Feet + 13 Inch = %s' % (r4)

    (stepFactors2, sysUnits2) = ms_ui_parse_unitconversiondesc('OZ 2 TBSP 3 TSP')
    r5 = ms_ui_equal('1 OZ 10 TSP', '2 OZ 1 TBSP 1 TSP', sysUnits2, stepFactors2)
    print '1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? %s' % (r5)
    r6 = ms_ui_add('1 OZ', '3 TBSP 3 TSP', sysUnits2, stepFactors2)
    print '1 OZ + 3 TBSP 3 TSP = %s' % (r6)
    r7 = ms_ui_scale('3', '1 OZ 10 TSP', sysUnits2, stepFactors2)
    print '3 * 1 OZ 10 TSP = %s' % (r7)
    r8 = ms_ui_add(ms_ui_scale('3', '1 OZ 10 TSP', sysUnits2, stepFactors2), '3 TBSP 1 TSP', sysUnits2, stepFactors2)
    print '3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = %s' % (r8)

    (stepFactors3, sysUnits3) = ms_ui_parse_unitconversiondesc('Day 24 Hour 60 Minute 60 Second')
    r9 = ms_ui_equal('7 Hour 59 Minute 60 Second', '8 Hour', sysUnits3, stepFactors3)
    print '7 Hour 59 Minute 60 Second == 8 Hour ? %s' % (r9)
    r10 = ms_ui_add('1 Day 23 Hour 59 Minute 59 Second', '1 Second', sysUnits3, stepFactors3)
    print '1 Day 23 Hour 59 Minute 59 Second + 1 Second = %s' % (r10)
    r11 = ms_ui_scale('2', '11 Hour 22 Minute 33 Second', sysUnits3, stepFactors3)
    print '2 * 11 Hour 22 Minute 33 Second = %s' % (r11)
    r12 = ms_ui_add(ms_ui_scale('2', '11 Hour 22 Minute 33 Second', sysUnits3, stepFactors3), '60 Second', sysUnits3, stepFactors3)
    print '2 * 11 Hour 22 Minute 33 Second + 60 Second = %s' % (r12)

    (stepFactors4, sysUnits4) = ms_ui_parse_unitconversiondesc('Mile 4 Miya 440 Yard 3 Feet 12 Inch')
    r13 = ms_ui_equal('4 Miya 446 Yard 40 Inch', '1 Mile 1 Miya 7 Yard 4 Inch', sysUnits4, stepFactors4)
    print '4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? %s' % (r13)
    r14 = ms_ui_add('3 Miya 13 Inch', '445 Yard 11 Inch', sysUnits4, stepFactors4)
    print '3 Miya 13 Inch + 445 Yard 11 Inch = %s' % (r14)
    r15 = ms_ui_add(ms_ui_scale('2', '3 Miya 13 Inch', sysUnits4, stepFactors4), '445 Yard 11 Inch', sysUnits4, stepFactors4)
    print '2 * 3 Miya 13 Inch + 445 Yard 11 Inch = %s' % (r15)

if __name__ == "__main__":
    main()
