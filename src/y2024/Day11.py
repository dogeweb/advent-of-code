from functools import cache
from collections import Counter, defaultdict

def next_val(n: str):
    return ["1"] if n == "0" else [n[:len(n) // 2], n[len(n) // 2:].lstrip('0') or "0"] if len(n) % 2 == 0 else [str(int(n) * 2024)]

def map_solution(input_str: str, times: int):

    counter = Counter(input_str.split())

    for _ in range(times):
        new_counter = Counter()
        for num, count in counter.items():
            for next_num in next_val(num):
                new_counter[next_num] += count
        counter = new_counter

    return sum(counter.values())


def dfs_solution(input_str: str, times: int):
    @cache
    def dfs(n: str, depth: int = times): return 1 if depth == 0 else sum(dfs(n, depth - 1) for n in next_val(n))

    return sum(map(dfs, input_str.split()))

def read_input(filename: str):
    with open(filename, "r") as f:
        return f.read().splitlines()

if __name__ == "__main__":
    test_input = read_input("Day11_test.txt")[0]
    assert map_solution(test_input, 6) == 22
    assert dfs_solution(test_input, 6) == 22
    assert map_solution(test_input, 25) == 55312
    assert dfs_solution(test_input, 25) == 55312

    input_data = read_input("Day11.txt")[0]
    from time import time

    start = time()
    print("map= ", map_solution(input_data, 25), f"(Time: {time() - start:.4f}s)")

    start = time()
    print("dfs= ", dfs_solution(input_data, 25), f"(Time: {time() - start:.4f}s)")

    start = time()
    print("map= ", map_solution(input_data, 75), f"(Time: {time() - start:.4f}s)")

    start = time()
    print("dfs= ", dfs_solution(input_data, 75), f"(Time: {time() - start:.4f}s)")