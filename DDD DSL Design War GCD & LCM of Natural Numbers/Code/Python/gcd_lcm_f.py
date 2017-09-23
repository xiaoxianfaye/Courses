## gcd lcm
def gcd(nums):
    return unified_proc('GCD', nums)

def lcm(nums):
    return unified_proc('LCM', nums)

## Specification
def spec():
    return  {
                'GCD': {'EXTRACTION_TYPE_PF': lambda listOfPfs: extract_common_prime_factors(listOfPfs), 
                        'EXTRACTION_TYPE_POWER': lambda ps: extract_min_power(ps)},
                'LCM': {'EXTRACTION_TYPE_PF': lambda listOfPfs: extract_allnp_prime_factors(listOfPfs),
                        'EXTRACTION_TYPE_POWER': lambda ps: extract_max_power(ps)},
            }

## Unified Process
def unified_proc(solutionType, nums):
    (extractPfFunc, extractPowerFunc) = extract_spec_funcs(solutionType, spec())
    (listOfPfs, listOfPfPs) = prime_factorize_list(nums)
    pfs = extract_prime_factors(extractPfFunc, listOfPfs)
    pfps = extract_powers(extractPowerFunc, pfs, listOfPfPs)
    return product_powers(pfps)

## Extract Specification Items
def extract_spec_funcs(solutionType, spec):
    extractPfFunc = spec[solutionType]['EXTRACTION_TYPE_PF']
    extractPowerFunc = spec[solutionType]['EXTRACTION_TYPE_POWER']
    return (extractPfFunc, extractPowerFunc)

# Prime Factorize
def prime_factorize_list(nums):
    listOfPfPs = [prime_factorize(num) for num in nums]
    listOfPfsAndPs = [unzip(pfps) for pfps in listOfPfPs]
    (listOfPfs, _listOfPs) = unzip(listOfPfsAndPs)
    return (listOfPfs, listOfPfPs)

def prime_factorize(num):
    pfps = []
    prime_factorize_tr(num, pfps)
    return pfps

def prime_factorize_tr(num, pfps):
    firstPf = extract_first_prime_factor(num, pfps)
    if firstPf:
        prime_factorize_tr(num / firstPf, pfps)

def extract_first_prime_factor(num, pfps):
    for i in range(2, num + 1):
        if is_prime(i) and is_factor(i, num):
            acc_prime_factors(i, pfps)
            return i

def acc_prime_factors(pf, pfps):
    pfp = key_find(pf, pfps)
    if pfp:
        key_replace(pf, pfps, (pf, pfp[1] + 1))
    else:
        pfps.append((pf, 1))

# Extract Prime Factors According to the Specification
def extract_prime_factors(func, listOfPfs):
    return func(listOfPfs)

def extract_common_prime_factors(listOfPfs):
    return intersect(listOfPfs)

def extract_allnp_prime_factors(listOfPfs):
    return union(listOfPfs)

# Extract Powers of the Extracted Prime Factors According to the Specification
def extract_powers(func, pfs, listOfPfPs):
    return [(pf, extract_power(func, extract_pf_powers(pf, listOfPfPs))) for pf in pfs]

def extract_pf_powers(pf, listOfPfPs):
    return [extract_pf_power(pf, pfps) for pfps in listOfPfPs]

def extract_pf_power(pf, pfps):
    pfp = key_find(pf, pfps)
    if pfp:
        return pfp[1]
    return 0

def extract_power(func, ps):
    return func(ps)

def extract_min_power(ps):
    return min(ps)

def extract_max_power(ps):
    return max(ps)

# Product of Power Results
def product_powers(pfps):
    return reduce(lambda acc, pfp: pow(pfp[0], pfp[1]) * acc, pfps, 1)

## Math Operations
def is_prime(num):
    return all(map(lambda x: num % x != 0, range(2, num)))

def is_factor(num1, num2):
    return num2 % num1 == 0

## List Operations
def key_find(key, pairs):
    pair = filter(lambda pair: key == pair[0], pairs)
    if len(pair):
        return pair[0]
    else:
        None

def key_replace(key, pairs, newPair):
    pair = key_find(key, pairs)
    if pair:
        idx = pairs.index(pair)
        pairs[idx] = newPair

def unzip(pairs):
    (firsts, seconds) = zip(*pairs)
    return (list(firsts), list(seconds))

def intersect(lists):
    if lists == []:
        return []

    if len(lists) == 1:
        return []

    return reduce(lambda acc, listE: intersect2(acc, listE), lists)

def intersect2(list1, list2):
    return list(set(list1) & set(list2))

def union(lists):
    if lists == []:
        return []

    if len(lists) == 1:
        return lists[0]

    return reduce(lambda acc, listE: union2(acc, listE), lists)

def union2(list1, list2):
    return list(set(list1) | set(list2))

## Tests
# Math Operation Tests
def test_is_prime():
    assert is_prime(2)
    assert is_prime(3)
    assert not is_prime(4)
    assert is_prime(5)
    
    print 'test_is_prime ok'

def test_is_factor():
    assert is_factor(1, 3)
    assert not is_factor(2, 3)
    assert is_factor(3, 3)
    assert is_factor(2, 6)

    print 'test_is_factor ok'

# List Operation Tests
def test_key_find():
    pairs = [(1, 'a'), (2, 'b')]
    assert (1, 'a') == key_find(1, pairs)
    assert None == key_find(3, pairs)

    print 'test_key_find ok'

def test_key_replace():
    pairs = [(1, 'a'), (2, 'b')]
    key_replace(2, pairs, (2, 'bb'))
    assert [(1, 'a'), (2, 'bb')] == pairs

    pairs = [(1, 'a'), (2, 'b')]
    key_replace(3, pairs, (3, 'c'))
    assert [(1, 'a'), (2, 'b')] == pairs

    print 'test_key_replace ok'

def test_unzip():
    pairs = [(1, 'a'), (2, 'b')]
    assert ([1, 2], ['a', 'b']) == unzip(pairs)

    print 'test_unzip ok'

def test_intersect():
    assert [] == intersect2([], [])
    assert [] == intersect2([1], [])
    assert [] == intersect2([], [1])
    assert [2, 3] == intersect2([1, 2, 3, 5], [2, 3, 4])
    assert [] == intersect2([1, 2, 3], [4, 5])

    assert [] == intersect([])
    assert [] == intersect([[1]])

    assert [2, 3] == intersect([[1, 2, 3, 5], [2, 3, 4]])
    assert [] == intersect([[1, 2, 3], [4, 5]])
    assert [3, 4] == intersect([[1, 2, 3, 4], [3, 4, 5], [3, 4, 5, 6]])

    print 'test_intersect ok'

def test_union():
    assert [] == union2([], [])
    assert [1] == union2([1], [])
    assert [1] == union2([], [1])
    assert [1, 2, 3, 4] == union2([1, 3], [2, 4])
    assert [1, 2, 3, 4] == union2([1, 2, 3], [3, 4])
    assert [1, 2, 3, 4, 5] == union2([1, 2, 3], [2, 3, 4, 5])

    assert [] == union([])
    assert [1] == union([[1]])
    assert [1, 2, 3, 4] == union([[1, 3], [2, 4]])
    assert [1, 2, 3, 4] == union([[1, 2, 3], [3, 4]])
    assert [1, 2, 3, 4, 5] == union([[1, 2, 3], [2, 3, 4, 5]])
    assert [1, 2, 3, 4, 5, 6] == union([[1, 2, 3], [3, 4], [4, 5, 6]])

    print 'test_union ok' 

# GCD and LCM Tests
def test_prime_factorize():
    assert [(2, 1)] == prime_factorize(2)
    assert [(2, 1), (3, 1)] == prime_factorize(6)

    assert [(2, 1), (3, 2), (5, 1)] == prime_factorize(90)
    assert [(2, 2), (3, 1), (5, 1), (7, 1)] == prime_factorize(420)
    assert [(2, 1), (3, 3), (5, 2), (7, 1)] == prime_factorize(9450)

    print 'test_prime_factorize ok'

def test_prime_factorize_list():
    assert ([[2, 3, 5], [2, 3, 5, 7], [2, 3, 5, 7]],
            [[(2, 1), (3, 2), (5, 1)], 
             [(2, 2), (3, 1), (5, 1), (7, 1)], 
             [(2, 1), (3, 3), (5, 2), (7, 1)]]
           ) == prime_factorize_list([90, 420, 9450])

    print 'test_prime_factorize_list ok'

def test_extract_prime_factors():
    inputListOfPfs = [[2, 3, 5], [2, 3, 5, 7], [2, 3, 5, 7]]
    assert [2, 3, 5] == extract_prime_factors(lambda listOfPfs: extract_common_prime_factors(listOfPfs), inputListOfPfs)
    assert [2, 3, 5, 7] == extract_prime_factors(lambda listOfPfs: extract_allnp_prime_factors(listOfPfs), inputListOfPfs)

    print 'test_extract_prime_factors ok'

def test_extract_powers():
    pfs = [2, 3, 5, 7]
    listOfPfPs = [[(2, 1), (3, 2), (5, 1)], 
                  [(2, 2), (3, 1), (5, 1), (7, 1)], 
                  [(2, 1), (3, 3), (5, 2), (7, 1)]]

    assert [(2, 1), (3, 1), (5, 1), (7, 0)] == extract_powers(lambda ps: extract_min_power(ps), pfs, listOfPfPs)
    assert [(2, 2), (3, 3), (5, 2), (7, 1)] == extract_powers(lambda ps: extract_max_power(ps), pfs, listOfPfPs)

    print 'test_extract_powers ok'

def test_product_powers():
    assert 30 == product_powers([(2, 1), (3, 1), (5, 1)])
    assert 18900 == product_powers([(2, 2), (3, 3), (5, 2), (7, 1)])

    print 'test_product_powers ok'

def test_gcd():
    assert 30 == gcd([90, 420, 9450])

    print 'test_gcd ok'

def test_lcm():
    assert 18900 == lcm([90, 420, 9450])

    print 'test_lcm ok'

def test():
    test_is_prime()
    test_is_factor()

    test_key_find()
    test_key_replace()
    test_unzip()
    test_intersect()
    test_union()

    test_prime_factorize()
    test_prime_factorize_list()
    test_extract_prime_factors()
    test_extract_powers()
    test_product_powers()

    test_gcd()
    test_lcm()

    print 'test ok'

test()