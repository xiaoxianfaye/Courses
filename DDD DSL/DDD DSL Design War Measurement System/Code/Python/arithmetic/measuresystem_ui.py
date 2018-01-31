import re
import string

import measuresystem as ms

PLACEHOLDER = 0x7FFFFFFF

def parse_unitconversion_desc(desc):
    tokens = re.split(r' +', string.strip(desc))
    stepfactors = [PLACEHOLDER]
    sysunits = [tokens[0]]
    for i in range(1, len(tokens), 2):
        stepfactors.append(int(tokens[i]))
        sysunits.append(tokens[i + 1])
    return (stepfactors, sysunits)

def parse_unitconversion_desc2(desc):
    tokens = re.split(r' +', string.strip(desc))
    return reduce(_acc_parse_unitconversion_desc2, tokens[1:], ([PLACEHOLDER], [tokens[0]]))

def _acc_parse_unitconversion_desc2((stepfactors, sysunits), token):
    stepfactors.append(int(token)) if token.isdigit() else sysunits.append(token)
    return stepfactors, sysunits

def parse_quantity(desc, sysunits):
    (values, units) = _parse_quantity_values_and_units(desc)
    return [values[units.index(sysunit)] if sysunit in units else 0 for sysunit in sysunits]

def _parse_quantity_values_and_units(desc):
    tokens = re.split(r' +', string.strip(desc))
    values = []
    units = []
    for i in range(0, len(tokens), 2):
        values.append(int(tokens[i]))
        units.append(tokens[i + 1])
    return values, units

def _parse_quantity_values_and_units2(desc):
    tokens = re.split(r' +', string.strip(desc))
    return reduce(_acc_parse_quantity_values_and_units2, tokens, ([], []))

def _acc_parse_quantity_values_and_units2((values, units), token):
    values.append(int(token)) if token.isdigit() else units.append(token)
    return values, units

def equal(quantity1, quantity2, sysunits, stepfactors):
    return ms.equal(parse_quantity(quantity1, sysunits),
                    parse_quantity(quantity2, sysunits),
                    ms.basefactors(stepfactors))

def format(quantityvec, sysunits):
    valueunits = [' '.join([str(value), unit])
                  for (value, unit) in zip(quantityvec, sysunits)
                  if value != 0 or unit == sysunits[-1]]
    return ' '.join(valueunits)

def add(quantity1, quantity2, sysunits, stepfactors):
    return format(ms.add(parse_quantity(quantity1, sysunits),
                         parse_quantity(quantity2, sysunits), ms.basefactors(stepfactors)),
                  sysunits)

def base_format(quantityvec, sysunits, stepfactors):
    basevalue = str(ms.base(quantityvec, ms.basefactors(stepfactors)))
    return ' '.join([basevalue, sysunits[-1]])

def scale(c, quantity, sysunits, stepfactors):
    return format(ms.scale(int(c), parse_quantity(quantity, sysunits), ms.basefactors(stepfactors)), sysunits)

if __name__ == '__main__':
    stepfactors, sysunits = parse_unitconversion_desc('Mile 1760 Yard 3 Feet 12 Inch')
    r1 = equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch", sysunits, stepfactors)
    print '1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? %s' % (r1)
    r2 = add('13 Inch', '11 Inch', sysunits, stepfactors)
    print '13 Inch + 11 Inch = %s' % (r2)
    r3 = scale('2', '2 Yard 3 Feet', sysunits, stepfactors)
    print '2 * 2 Yard 3 Feet = %s' % (r3)
    r4 = add(scale('2', '2 Yard 3 Feet', sysunits, stepfactors), '13 Inch', sysunits, stepfactors)
    print '2 * 2 Yard 3 Feet + 13 Inch = %s' % (r4)

    stepfactors2, sysunits2 = parse_unitconversion_desc('OZ 2 TBSP 3 TSP')
    r5 = equal('1 OZ 10 TSP', '2 OZ 1 TBSP 1 TSP', sysunits2, stepfactors2)
    print '1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? %s' % (r5)
    r6 = add('1 OZ', '3 TBSP 3 TSP', sysunits2, stepfactors2)
    print '1 OZ + 3 TBSP 3 TSP = %s' % (r6)
    r7 = scale('3', '1 OZ 10 TSP', sysunits2, stepfactors2)
    print '3 * 1 OZ 10 TSP = %s' % (r7)
    r8 = add(scale('3', '1 OZ 10 TSP', sysunits2, stepfactors2), '3 TBSP 1 TSP', sysunits2, stepfactors2)
    print '3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = %s' % (r8)

    # Homework
    stepfactors3, sysunits3 = parse_unitconversion_desc('Day 24 Hour 60 Minute 60 Second')
    r9 = equal('7 Hour 59 Minute 60 Second', '8 Hour', sysunits3, stepfactors3)
    print '7 Hour 59 Minute 60 Second == 8 Hour ? %s' % (r9)
    r10 = add('1 Day 23 Hour 59 Minute 59 Second', '1 Second', sysunits3, stepfactors3)
    print '1 Day 23 Hour 59 Minute 59 Second + 1 Second = %s' % (r10)
    r11 = scale('2', '11 Hour 22 Minute 33 Second', sysunits3, stepfactors3)
    print '2 * 11 Hour 22 Minute 33 Second = %s' % (r11)
    r12 = add(scale('2', '11 Hour 22 Minute 33 Second', sysunits3, stepfactors3), '60 Second', sysunits3, stepfactors3)
    print '2 * 11 Hour 22 Minute 33 Second + 60 Second = %s' % (r12)

    # Homework
    stepfactors4, sysunits4 = parse_unitconversion_desc('Mile 4 Miya 440 Yard 3 Feet 12 Inch')
    r13 = equal('4 Miya 446 Yard 40 Inch', '1 Mile 1 Miya 7 Yard 4 Inch', sysunits4, stepfactors4)
    print '4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? %s' % (r13)
    r14 = add('3 Miya 13 Inch', '445 Yard 11 Inch', sysunits4, stepfactors4)
    print '3 Miya 13 Inch + 445 Yard 11 Inch = %s' % (r14)
    r15 = add(scale('2', '3 Miya 13 Inch', sysunits4, stepfactors4), '445 Yard 11 Inch', sysunits4, stepfactors4)
    print '2 * 3 Miya 13 Inch + 445 Yard 11 Inch = %s' % (r15)