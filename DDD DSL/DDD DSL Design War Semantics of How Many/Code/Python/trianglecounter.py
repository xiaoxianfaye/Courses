from setop import belong
from combinator import combinate

def connected(p1, p2, lines):
    return belong([p1, p2], lines)

def on_a_line(p1, p2, p3, lines):
    return belong([p1, p2, p3], lines)

def triangle(p1, p2, p3, lines):
    return connected(p1, p2, lines) and \
           connected(p2, p3, lines) and \
           connected(p1, p3, lines) and \
           (not on_a_line(p1, p2, p3, lines))

def inner_count(points, lines):
    return len([triplepoints for triplepoints in combinate(points, 3)
                if triangle(triplepoints[0], triplepoints[1], triplepoints[2], lines)])

def parse_points(points):
    return [point for point in points]

def parse_lines(lines):
    return [parse_points(line) for line in lines]

def count(points, lines):
    return inner_count(parse_points(points), parse_lines(lines))