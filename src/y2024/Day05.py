import functools
import time
from itertools import takewhile


def read_input(filename: str):
    with open(filename, "r") as f:
        return f.read().splitlines()


def solve(input_lines, part2: bool = False) -> int:
    order = {(line[0:2], line[3:5]) for line in takewhile(str.strip, input_lines)}

    return sum(int(sorted_nums[len(nums) // 2]) for nums in (line.split(',') for line in input_lines[len(order) + 1:])
               if (sorted_nums := sorted(nums, key=functools.cmp_to_key(lambda a, b: -1 if (a, b) in order else 1 if (b, a) in order else 0))) and (nums == sorted_nums) != part2)


def part1(input_lines): return solve(input_lines)


def part2(input_lines): return solve(input_lines, part2=True)


test_input = read_input("Day05_test.txt")
result1 = part1(test_input)
result2 = part2(test_input)
print(result1)
print(result2)
assert result1 == 143
assert result2 == 123

input_lines = read_input("Day05.txt")

start_ns = time.perf_counter_ns()
res1 = part1(input_lines)
print(f"{res1} | {(time.perf_counter_ns() - start_ns) / 1_000_000:.3f} ms")

start_ns = time.perf_counter_ns()
res2 = part2(input_lines)
print(f"{res2} | {(time.perf_counter_ns() - start_ns) / 1_000_000:.3f} ms")