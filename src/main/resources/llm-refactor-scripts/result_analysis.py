import json

import matplotlib.pyplot as plt
import numpy as np

img_path_pattern = 'images/{}.png'


def load_data(file_path):
    """从指定路径加载JSON数据"""
    with open(file_path, 'r', encoding='utf-8') as file:
        data = json.load(file)
    return data['datas']


def plot_problem_counts(data):
    """绘制不同策略的'Problem Count'数据的折线图"""
    strategies = [item['Strategy'] for item in data]
    problem_counts = [item['Problem Count'] for item in data]

    # 确保策略名称与问题计数列表一一对应
    assert len(strategies) == len(problem_counts), "策略数量与问题计数列表长度不匹配"

    plt.figure(figsize=(12, 6))

    # 绘制折线图
    for i, strategy in enumerate(strategies):
        plt.plot(range(1, len(problem_counts[i]) + 1), problem_counts[i], label=strategy, marker='o')

    plt.title('Problem Counts by Different Strategies')
    plt.xlabel('Data Set Index')
    plt.ylabel('Problem Count')
    plt.legend()
    plt.grid(True)
    plt.savefig(img_path_pattern.format("result_problem_count"))


def plot_rule_category_pie_charts(data):
    """为每个策略的'Rule Category Counts'字段绘制饼状图"""
    categories = ["Design", "Multithreading", "Documentation", "Best Practices", "Code Style",
                  "Performance", "Error Prone", ]

    for item in data:
        strategy = item['Strategy']

        # 获取当前策略的分类计数
        counts = item['Rule Category Counts']

        # 检查是否有缺失的类别，并用0填充
        for category in categories:
            if category not in counts:
                counts[category] = 0

        # 绘制饼状图
        fig, ax = plt.subplots()
        ax.pie(counts.values(), autopct='%1.1f%%', startangle=140)
        ax.axis('equal')  # Equal aspect ratio ensures that pie is drawn as a circle.

        # 添加标题并显示图例
        title = f"Rule Category Distribution for Strategy '{item['Strategy']}'"
        ax.set_title(title)
        plt.legend(categories, title="Categories", loc="upper right", bbox_to_anchor=(1.2, 1))
        plt.savefig(img_path_pattern.format("result_pie_" + strategy))


def plot_stacked_bar_chart_on_ruleset_count(data):
    """绘制不同类别的数量柱形图"""
    title = "Rule Category Counts Across Strategies"
    categories = ["Design", "Multithreading", "Documentation", "Best Practices", "Code Style",
                  "Performance", "Error Prone"]

    values = []
    print(data)
    for datum in data:
        values.append([datum['Rule Category Counts'].get(category, 0) for category in categories])

    # 设置柱形图宽度和间距，减小柱形宽度以留出更多间隙
    bar_width = 0.15
    index = np.arange(len(categories))

    # 绘制柱形图
    fig, ax = plt.subplots()

    # 绘制柱形图，并为每个数据集添加间隔
    for i, (Strategy, value) in enumerate(zip([d['Strategy'] for d in data], values)):
        bars = ax.bar(index + i * bar_width, value, bar_width, label=Strategy)

    # 添加一些文本和格式设置
    ax.set_xlabel('Categories')
    ax.set_ylabel('Counts')
    ax.set_title(title)

    # 设置x轴标签倾斜
    ax.set_xticks(index + bar_width / 2)  # 调整位置以居中于每个柱子
    ax.set_xticklabels(categories, rotation=45, ha='right')  # 旋转x轴标签并设置水平对齐为右对齐

    ax.legend()

    # 优化布局，考虑标签旋转后的空间
    plt.tight_layout(pad=2, rect=[0, 0, 1, 0.95])

    # 展示图表
    plt.savefig(img_path_pattern.format("result_rule_bar"))


def plot_stacked_bar_char_on_priority(data):
    """绘制不同优先级的数量柱形图"""
    title = "Priorities Counts Across Strategies"
    categories = ["1", "2", "3", "4"]

    values = []
    # print(data)
    for datum in data:
        values.append([datum['Priority Counts'].get(category, 0) for category in categories])

    # 设置柱形图宽度和间距
    bar_width = 0.15
    index = np.arange(len(categories))

    # 绘制柱形图
    fig, ax = plt.subplots()

    # 设置柱形图宽度和间距，减小柱形宽度以留出更多间隙
    # bar_width = 0.25
    # index = np.arange(len(categories))

    # 绘制柱形图，并为每个数据集添加间隔
    for i, (Strategy, value) in enumerate(zip([d['Strategy'] for d in data], values)):
        bars = ax.bar(index + i * bar_width, value, bar_width, label=Strategy)

    # 添加一些文本和格式设置
    ax.set_xlabel('Priority')
    ax.set_ylabel('Counts')
    ax.set_title(title)

    # 设置x轴标签倾斜
    priorities = ["High (1)", "Medium High (2)", "Medium (3)", "Medium Low (4)"]
    ax.set_xticks(index + bar_width / 2)  # 调整位置以居中于每个柱子
    ax.set_xticklabels(priorities, rotation=45, ha='right')  # 旋转x轴标签并设置水平对齐为右对齐

    ax.legend()

    # 优化布局，考虑标签旋转后的空间
    plt.tight_layout(pad=2, rect=[0, 0, 1, 0.95])

    plt.savefig(img_path_pattern.format("result_priority_bar"))


if __name__ == "__main__":
    file_path = 'data/rq2/compare-performance.json'
    data = load_data(file_path)
    # plot_problem_counts(data)
    # plot_rule_category_pie_charts(data)
    plot_stacked_bar_chart_on_ruleset_count(data)
    # plot_stacked_bar_char_on_priority(data)
