import re
import string

import measuresystem as ms

PLACEHOLDER = 0x7FFFFFFF

def parse_unitconversion_desc(desc):
    (sysunits, stepfactorstrs) = partitioning_by_index_parity(tokenize(desc))
    stepfactors = ints(stepfactorstrs)
    stepfactors.insert(0, PLACEHOLDER)
    return stepfactors, sysunits

def parse_unitconversion_desc2(desc):
    tokens = tokenize(desc)
    (sysunits, stepfactorstrs, tokens) = reduce(_acc_parse_desc, tokens, ([], [], tokens))
    stepfactors = ints(stepfactorstrs)
    stepfactors.insert(0, PLACEHOLDER)
    return stepfactors, sysunits

def parse_quantity_desc(desc, sysunits):
    (valuestrs, units) = partitioning_by_index_parity(tokenize(desc))
    values = ints(valuestrs)
    return [values[units.index(sysunit)] if sysunit in units else 0 for sysunit in sysunits]

def parse_quantity_desc2(desc, sysunits):
    tokens = tokenize(desc)
    (valuestrs, units, tokens) = reduce(_acc_parse_desc, tokens, ([], [], tokens))
    values = ints(valuestrs)
    return [values[units.index(sysunit)] if sysunit in units else 0 for sysunit in sysunits]

def equal(quantitydesc1, quantitydesc2, sysunits, stepfactors):
    return ms.equal(parse_quantity_desc(quantitydesc1, sysunits),
                    parse_quantity_desc(quantitydesc2, sysunits),
                    stepfactors)

def format(quantityvec, sysunits):
    valueunits = [' '.join([str(value), unit])
                  for (value, unit) in zip(quantityvec, sysunits)
                  if value != 0 or unit == sysunits[-1]]
    return ' '.join(valueunits)

def add(quantitydesc1, quantitydesc2, sysunits, stepfactors):
    return format(ms.add(parse_quantity_desc(quantitydesc1, sysunits),
                         parse_quantity_desc(quantitydesc2, sysunits), stepfactors),
                  sysunits)

def base_format(quantityvec, sysunits, stepfactors):
    basevalue = str(ms.base(quantityvec, ms.basefactors(stepfactors)))
    return ' '.join([basevalue, sysunits[-1]])

def scale(c, quantitydesc, sysunits, stepfactors):
    return format(ms.scale(int(c), parse_quantity_desc(quantitydesc, sysunits), stepfactors), sysunits)

def tokenize(desc):
    return re.split(r' +', string.strip(desc))

def partitioning_by_index_parity(tokens):
    part1 = []
    part2 = []
    for token in tokens:
        part1.append(token) if evenindex(token, tokens) else part2.append(token)
    return part1, part2

def evenindex(token, tokens):
    return tokens.index(token) % 2 == 0

def ints(strs):
    return [int(s) for s in strs]

def _acc_parse_desc((part1, part2, tokens), token):
    part1.append(token) if evenindex(token, tokens) else part2.append(token)
    return part1, part2, tokens

if __name__ == '__main__':
    implen_stepfactors, implen_sysunits = parse_unitconversion_desc('Mile 1760 Yard 3 Feet 12 Inch')
    r1 = equal("1765 Yard 40 Inch", "1 Mile 6 Yard 4 Inch", implen_sysunits, implen_stepfactors)
    print '1765 Yard 40 Inch == 1 Mile 6 Yard 4 Inch ? %s' % (r1)
    r2 = add('13 Inch', '11 Inch', implen_sysunits, implen_stepfactors)
    print '13 Inch + 11 Inch = %s' % (r2)

    impvol_stepfactors, impvol_sysunits = parse_unitconversion_desc('OZ 2 TBSP 3 TSP')
    r3 = equal('1 OZ 10 TSP', '2 OZ 1 TBSP 1 TSP', impvol_sysunits, impvol_stepfactors)
    print '1 OZ 10 TSP == 2 OZ 1 TBSP 1 TSP ? %s' % (r3)
    r4 = add('1 OZ', '3 TBSP 3 TSP', impvol_sysunits, impvol_stepfactors)
    print '1 OZ + 3 TBSP 3 TSP = %s' % (r4)

    time_stepfactors, time_sysunits = parse_unitconversion_desc('Day 24 Hour 60 Minute 60 Second')
    r5 = equal('7 Hour 59 Minute 60 Second', '8 Hour', time_sysunits, time_stepfactors)
    print '7 Hour 59 Minute 60 Second == 8 Hour ? %s' % (r5)
    r6 = add('1 Day 23 Hour 59 Minute 59 Second', '1 Second', time_sysunits, time_stepfactors)
    print '1 Day 23 Hour 59 Minute 59 Second + 1 Second = %s' % (r6)

    implen2_stepfactors, implen2_sysunits = parse_unitconversion_desc('Mile 4 Miya 440 Yard 3 Feet 12 Inch')
    r7 = equal('4 Miya 446 Yard 40 Inch', '1 Mile 1 Miya 7 Yard 4 Inch', implen2_sysunits, implen2_stepfactors)
    print '4 Miya 446 Yard 40 Inch == 1 Mile 1 Miya 7 Yard 4 Inch ? %s' % (r7)
    r8 = add('3 Miya 13 Inch', '445 Yard 11 Inch', implen2_sysunits, implen2_stepfactors)
    print '3 Miya 13 Inch + 445 Yard 11 Inch = %s' % (r8)

    r9 = scale('2', '2 Yard 3 Feet', implen_sysunits, implen_stepfactors)
    print '2 * 2 Yard 3 Feet = %s' % (r9)
    r10 = add(scale('2', '2 Yard 3 Feet', implen_sysunits, implen_stepfactors), '13 Inch', implen_sysunits, implen_stepfactors)
    print '2 * 2 Yard 3 Feet + 13 Inch = %s' % (r10)

    r11 = scale('3', '1 OZ 10 TSP', impvol_sysunits, impvol_stepfactors)
    print '3 * 1 OZ 10 TSP = %s' % (r11)
    r12 = add(scale('3', '1 OZ 10 TSP', impvol_sysunits, impvol_stepfactors), '3 TBSP 1 TSP', impvol_sysunits, impvol_stepfactors)
    print '3 * 1 OZ 10 TSP + 3 TBSP 1 TSP = %s' % (r12)