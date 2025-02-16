import heapq

def parse_input(input):
    return [tuple(map(int, line.strip().split(','))) for line in input if line.strip()]

def dist(nodes, end):
    distances = dict()
    queue = [(0, 0, 1)]
    directions = [(0, 1), (1, 0), (-1, 0), (0, -1)]

    while queue:
        cost, x, y = heapq.heappop(queue)

        if (x, y) == end:
            return cost

        for dx, dy in directions:
            nx, ny = x + dx, y + dy

            if 0 <= nx <= end[0] and 0 <= ny <= end[1] and (nx, ny) not in nodes:
                new_cost = cost + 1
                if (nx, ny) not in distances or new_cost < distances[(nx, ny)]:
                    distances[(nx, ny)] = new_cost
                    heapq.heappush(queue, (new_cost, nx, ny))

    return None

def part1(input, bytes):
    nodes = parse_input(input[:bytes])

    end = (max(a[0] for a in nodes), max(a[1] for a in nodes))
    return dist(nodes, end)

def part2(input):
    nodes = parse_input(input)
    end = (max(a[0] for a in nodes), max(a[1] for a in nodes))
    l, h = 1, len(nodes)

    while l <= h:
        m = (l + h) // 2
        if not dist(nodes[:m], end):
            h = m - 1
        else:
            l = m + 1

    return input[l-1]



testInput = open("Day18_test.txt", "r").readlines()
input = open("Day18.txt", "r").readlines()

print(part1(testInput, 12))
print(part2(testInput))

print(part1(input, 1024))
print(part2(input))