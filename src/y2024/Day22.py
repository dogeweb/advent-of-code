from itertools import islice
from itertools import pairwise
from itertools import groupby
from time import time


def window(seq, n=4):
    it = iter(seq)
    result = tuple(islice(it, n))
    if len(result) == n:
        yield result
    for elem in it:
        result = result[1:] + (elem,)
        yield result


def generate_prices(seed: int):
    yield seed
    for _ in range(2000):
        seed = (seed << 6 ^ seed) % 16777216
        seed = (seed >> 5 ^ seed) % 16777216
        seed = (seed << 11 ^ seed) % 16777216
        yield seed


def part1(input_list):
    return sum(next(islice(generate_prices(int(x)), 2000, 2001)) for x in input_list)


def part2(input_list):
    diffs = []

    for x in input_list:
        sequence = [n % 10 for n in generate_prices(int(x))]
        pairs = [(b - a, b) for a, b in pairwise(sequence)]
        windowed = [((a[0], b[0], c[0], d[0]), d[1]) for a, b, c, d in window(pairs)]

        unique = {}
        for first, second in windowed:
            if first not in unique:
                unique[first] = second

        diffs.append(unique)

    grouped = {}
    for unique in diffs:
        for key, value in unique.items():
            grouped.setdefault(key, []).append(value)

    return max(sum(values) for values in grouped.values())


def read_input(filename):
    with open(filename) as f:
        return [line.strip() for line in f]

test_input = read_input("Day22_test.txt")
test_input2 = read_input("Day22_test2.txt")
p1 = part1(test_input)
print(p1)
assert p1 == 37327623
p2 = part2(test_input2)
print(p2)
assert p2 == 23

input_data = read_input("Day22.txt")
start = time()
print(part1(input_data), f"| Execution time: {time() - start:.6f}s")

start = time()
print(part2(input_data), f"| Execution time: {time() - start:.6f}s")
