import json

import matplotlib.pyplot as plt
import numpy as np
from scipy.interpolate import interp1d
from scipy.signal import savgol_filter

# 读取JSON文件
with open('data/rq1/compare-similarity.json', 'r') as file:
    data = json.load(file)

# 提取数据
temps = [item['temperature'] for item in data['datas'] if item['averages']]
avg_data = [[item['temperature'], avg] for item in data['datas'] if item['averages'] for avg in item['averages']]
var_data = [[item['temperature'], var] for item in data['datas'] if item['variances'] for var in item['variances']]

# 按温度分组数据
avg_grouped = {}
var_grouped = {}
for temp, val in avg_data:
    if temp not in avg_grouped:
        avg_grouped[temp] = []
    avg_grouped[temp].append(val)
for temp, val in var_data:
    if temp not in var_grouped:
        var_grouped[temp] = []
    var_grouped[temp].append(val)

# # 绘制平均值的折线图
# plt.figure(figsize=(12, 6))
# # 对每个温度的数据应用spline插值
# for temp, values in avg_grouped.items():
#     # 确保数据点是有序的，并且x轴（在这里是数据点索引）是连续的
#     x = np.arange(len(values))
#     f = interp1d(x, values, kind='cubic')  # 使用三次样条插值
#     x_new = np.linspace(0, len(values) - 1, 400)  # 生成更多数据点进行平滑
#     y_smooth = f(x_new)
#     plt.plot(x_new, y_smooth, label=f'Temperature {temp}', linewidth=2)
#
# plt.title('Averages over Temperature')
# plt.xlabel('Data Points (Interpolated)')
# plt.ylabel('Average Values')
# plt.legend()
# # plt.show()
# # 保存图片
# plt.savefig('images/require0/averages_plot.png')  # 保存为PNG格式

# 假设avg_grouped是一个字典，其中键是温度，值是对应温度下的数据列表
plt.figure(figsize=(12, 6))

# 为了演示，我们遍历每个温度组
for temp, values in avg_grouped.items():
    # 应用Savitzky-Golay平滑滤波
    window_size = 15
    polyorder = 5
    smooth_values = savgol_filter(values, window_size, polyorder)

    # 定义x轴（例如，原始数据点的索引或实际的x坐标）
    x_original = np.arange(len(values))  # 假设原始数据是等间距的

    # 创建插值函数
    interpolator = interp1d(x_original, smooth_values, kind='cubic')  # 这里使用线性插值，您可以改为'cubic'等其他类型

    # 生成更多插值点（例如，原始点数的两倍，以获得更平滑的曲线）
    x_interpolated = np.linspace(x_original.min(), x_original.max(), 400)

    # 使用插值函数计算新x坐标上的y值
    interpolated_values = interpolator(x_interpolated)

    plt.plot(x_interpolated, interpolated_values, label=f'Temperature {temp}', linewidth=2)

plt.title('Smoothed Averages over Temperature')
plt.xlabel('Data Points (Interpolated)')
plt.ylabel('Smoothed Average Values')
plt.legend()

# 保存图片
plt.savefig('images/require0/smoothed_averages_plot.png')  # 保存为PNG格式
# plt.show()


# 绘制方差的折线图
# plt.figure(figsize=(12, 6))
# for temp, values in var_grouped.items():
#     x = np.arange(len(values))
#     f = interp1d(x, values, kind='cubic')  # 使用三次样条插值
#     x_new = np.linspace(0, len(values) - 1, 400)  # 生成更多数据点进行平滑
#     y_smooth = f(x_new)
#     plt.plot(x_new, y_smooth, label=f'Temperature {temp}', linewidth=2)
#
# plt.title('Variances over Temperature')
# plt.xlabel('Data Points (Interpolated)')
# plt.ylabel('Variance Values')
# plt.legend()
# # 保存图片
# plt.savefig('images/require0/variances_plot.png')  # 保存为PNG格式


# # 绘制平滑的方差曲线
# plt.figure(figsize=(12, 6))
# for temp, values in var_grouped.items():
#     # 应用Savitzky-Golay平滑滤波
#     window_size = 15  # 窗口大小一般取数据长度的十分之一到五分之一，这里取十分之一
#     polyorder = 2  # 多项式阶数，一般取2或3
#     smooth_values = savgol_filter(values, window_size, polyorder)
#     plt.plot(smooth_values, label=f'Temperature {temp}', linewidth=2)
# plt.title('Smoothed Variance over Temperature')
# plt.xlabel('Data Points')
# plt.ylabel('Smoothed Variance Values')
# plt.legend()
# # 保存图片
# plt.savefig('images/smoothed_variance_plot.png')  # 保存为PNG格式


# 假设var_grouped是一个字典，键为温度，值为对应温度下的方差值列表
average_averages = {}  # 存储每个温度的方差平均值
average_variances = {}  # 存储每个温度的方差的方差
all_variances = []  # 用于存储所有温度的所有方差值，以便计算总体平均值

for temp, values in avg_grouped.items():
    average_avg = np.mean(values)
    average_std = np.var(values)
    average_averages[temp] = average_avg
    average_variances[temp] = average_std

for temp, values in var_grouped.items():
    all_variances.extend(values)

overall_variance_avg = np.mean(all_variances)

# 打印结果
print("每个温度的平均值的平均值:")
for temp, avg in average_averages.items():
    print(f"Temperature {temp}: {avg}")

print("\n每个温度的平均值的方差:")
for temp, var in average_variances.items():
    print(f"Temperature {temp}: {var}")

print(f"\n所有温度的方差总体平均值: {overall_variance_avg}")