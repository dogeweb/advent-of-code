from functools import cache
from itertools import pairwise
from time import time


def read_input(file_name):
    with open(file_name, "r") as f:
        return f.read().splitlines()


pad = {c: (i % 3, i // 3) for i, c in enumerate("789456123 0A ^K<v>")}


@cache
def paths(s: tuple, e: tuple):
    if s == e:
        return ["K"]
    result = []
    if s[0] < e[0]:
        result += map('>'.__add__, paths((s[0] + 1, s[1]), e))
    elif s[0] > e[0] and s not in [(1, 4), (1, 3)]:
        result += map('<'.__add__, paths((s[0] - 1, s[1]), e))
    if s[1] < e[1] and s != (0, 2):
        result += map('v'.__add__, paths((s[0], s[1] + 1), e))
    elif s[1] > e[1] and s != (0, 5):
        result += map('^'.__add__, paths((s[0], s[1] - 1), e))
    return result


def manhattan_distance_and_press(s, e): return abs(s[0] - e[0]) + abs(s[1] - e[1]) + 1


def solve(input, robots):
    @cache
    def dfs(s, i = robots):
        op = manhattan_distance_and_press if i == 1 else lambda a, b: min(dfs(p, i - 1) for p in paths(a, b))
        return sum(op(a, b) for a, b in pairwise(map(pad.get, f"K{s}")))

    return sum(sum(min(map(dfs, paths(a, b))) for a, b in pairwise(map(pad.get, f"A{it}"))) * int(it[:-1]) for it in input)


test_input = read_input("Day21_test.txt")

s1 = solve(test_input, 2)
print(s1)
assert (s1 == 126384)
s2 = solve(test_input, 25)
print(s2)

input_data = read_input("Day21.txt")
start_time = time()
print(solve(input_data, 2), "|", round((time() - start_time) * 1000, 2), "ms")
start_time = time()
print(solve(input_data, 25), "|", round((time() - start_time) * 1000, 2), "ms")
