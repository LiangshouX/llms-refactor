

import json
import matplotlib.pyplot as plt
import numpy as np

# 从文件加载JSON数据
with open('data/rq1/compare-performance.json', 'r', encoding='utf-8') as file:
    data = json.load(file)
title = data['title']
datas = data['datas']

# 准备数据和标签
categories = [ 'Design', 'Multithreading', 'Documentation', 'Best Practices',
              'Code Style', 'Performance', 'Error Prone']
values = []
for datum in datas:
    values.append([datum.get(category, 0) for category in categories])

# 设置柱形图宽度和间距
bar_width = 0.15
index = np.arange(len(categories))

# 绘制柱形图
fig, ax = plt.subplots()

# 设置柱形图宽度和间距，减小柱形宽度以留出更多间隙
# bar_width = 0.25
# index = np.arange(len(categories))

# 绘制柱形图，并为每个数据集添加间隔
for i, (data_title, value) in enumerate(zip([d['data_title'] for d in datas], values)):
    bars = ax.bar(index + i * bar_width, value, bar_width, label=data_title)

# 添加一些文本和格式设置
ax.set_xlabel('Categories')
ax.set_ylabel('Values')
ax.set_title(title)

# 设置x轴标签倾斜
ax.set_xticks(index + bar_width / 2)  # 调整位置以居中于每个柱子
ax.set_xticklabels(categories, rotation=45, ha='right')  # 旋转x轴标签并设置水平对齐为右对齐

ax.legend()

# 优化布局，考虑标签旋转后的空间
plt.tight_layout(pad=2, rect=[0, 0, 1, 0.95])

# 保存图表
image_path = 'images/{}.png'.format(title.replace(" ", "_"))
plt.savefig(image_path)
print(f"图表已保存至 {image_path}")

# 可选：显示图表
# plt.show()