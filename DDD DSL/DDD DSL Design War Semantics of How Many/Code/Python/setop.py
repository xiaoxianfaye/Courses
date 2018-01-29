def subset(s1, s2):
    return all([ele1 in s2 for ele1 in s1])

def belong(s, sos):
    return any([subset(s, ele) for ele in sos])