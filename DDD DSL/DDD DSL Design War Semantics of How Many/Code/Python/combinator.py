from itertools import combinations

def combinate(s, n):
    return [list(ele) for ele in combinations(s, n)]

def combinate2(s, n):
    if n == 1:
        return [[ele] for ele in s]

    if n == len(s):
        return [s]

    comb1 = [[s[0]] + ele for ele in combinate2(s[1:], n - 1)]
    comb2 = combinate2(s[1:], n)
    return comb1 + comb2