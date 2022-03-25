# 使用 pyecharts 来进行数据可视化，所以选择使用python
# 主要涉及到的技术有 pyecharts的图表制作以及时间轮播图。
# 本文件运行后生成 Covid_19Charts.html，使用浏览器渲染就可以看到数据可视化的效果了
# 本文件注释最后更新日期：2022-03-25

import pandas as pd
import json
from pyecharts import options as opts
from pyecharts.globals import ThemeType
from pyecharts.charts import Timeline, Bar, Map, Pie, Grid
import os
#<script type="text/javascript" src="chinaWithCityDetail.js"></script>
data = []

def get_data(date):
    dict = {}.fromkeys(['time','value'])
    dict['time'] = date
    jsonFile = open('../../../../../resources/static/'+date+'ConfirmData.json','r',encoding='utf-8')
    values = json.load(jsonFile)

    dict['value'] = values
    data.append(dict)



def covid_19_charts(date: str):
    # 处理数据
    map_data = [[[x["name"], x["value"]] for x in d["value"]] for d in data if d["time"] == date][0]
    # 地图
    map_chart = (
        Map()
            .add(
            layout_center=['90%','50%'],
            series_name='',
            data_pair=map_data,
            maptype='china',
            is_map_symbol_show=False, # 显示图形标记，即小红点
            label_opts=opts.LabelOpts(
                is_show=False, # 显示地名
                font_size=4.5,
                color='#747474'
            ),
            itemstyle_opts={  # 图元样式配置项
                "normal": {
                    # 显示的样式
                    "areaColor": "white",
                    "borderColor": "#404a59"
                },

                "emphasis": {
                    # 鼠标选中后显示的样式
                    "lable": {"show": Timeline},
                    "areaColor": "rgba(255,255,255, 0.5)"
                },
            }
        )
            .set_global_opts(
            title_opts=opts.TitleOpts(
                pos_left="80%",
                pos_top="top",
                title_textstyle_opts=opts.TextStyleOpts(
                    font_size='20',
                    color="rgba(155,155,155,0.9)"
                )
            ),
            tooltip_opts=opts.TooltipOpts(
                is_show=False # 不显示中间那个点
            ),
            visualmap_opts=opts.VisualMapOpts(
                orient='vertical',  # 垂直，可设置 水平 horizontal
                is_calculable=False, # 可拖拽
                dimension=0,    # 维度，多层图形时候有用
                pos_right='60%',  # 组件位置
                pos_bottom='bottom',  # 组件位置
                range_text=['max', 'min'],  # 组件两端的文本
                min_=0,  # 组件最小值
                max_=100000,  # 组件最大值
                is_piecewise=True,
                split_number=7,
                is_show=True,


                textstyle_opts=opts.TextStyleOpts(
                    color="rgba(0,0,0,0.5)",  # 文字颜色
                    font_size=5
                )
            )
        )
    )
    bar_data = [[[x["name"], x["value"]] for x in d["value"]] for d in data if d["time"] == date][0]
    #
    bar_data = sorted(bar_data,key=lambda x:x[1],reverse=True)
    if len(bar_data) > 10:
        bar_data = bar_data[0:10]
    if bar_data[len(bar_data)-1][0]=="null":
        bar_data = bar_data[0:len(bar_data)-1]
    bar_chart = (
        Bar()
            .add_xaxis(xaxis_data=[item[0] for item in bar_data])
            .add_yaxis(
            series_name='',
            y_axis=[item[1] for item in bar_data],
            label_opts=opts.LabelOpts(
                font_size=12,
                font_weight='bolder',
                position='right',
                formatter="{b}: {c}",
            )
        )
            #
            .set_global_opts(
            xaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(is_show=False)),
            yaxis_opts=opts.AxisOpts(axislabel_opts=opts.LabelOpts(is_show=False)),
            tooltip_opts=opts.TooltipOpts(is_show=False),
            title_opts=opts.TitleOpts(
                title='疫情数据可视化：' + date + '日 现有确诊 病例',
                pos_top='top',
                pos_left='center',
                title_textstyle_opts=opts.TextStyleOpts(
                    font_size=30,
                    font_weight='bold',
                    color="#FF9903FF"
                ),
            ),
            #
            visualmap_opts=opts.VisualMapOpts(
                orient='vertical',
                is_calculable=True,
                dimension=0,
                pos_left='85%',  # 组件位置
                pos_bottom='60%',  # 组件位置
                range_text=['', ''],  # 组件两端的文本
                is_piecewise=True,
                split_number=7,
                is_show=True,
                # range_color=["LightPink", "PaleVioletRed"],  # 组件过渡颜色
                min_=0,  # 组件最小值
                max_=100000,  # 组件最大值
                pieces=[
                    {"min":1,"max":9,"label": '1-9',"color":'#b4b09c'},
                    {"min":10,"max":99,"label":'10-99',"color":'#ecda7f'},
                    {"min":100,"max":499,"label":'100-499',"color":'#c2c148'},
                    {"min":500,"max":999,"label":'500-999',"color":'#efbd0a'},
                    {"min":1000,"max":4999,"label":'1000-4999',"color":'#782400'},
                    {"min":5000,"max":9999,"label":'5000-9999',"color":'#9a0008'},
                    {"min":10000,"max":1000000000,"label":'10000+',"color":'#420c0d'},
                ],
                textstyle_opts=opts.TextStyleOpts(
                    color="rgba(0,0,0,2)",  # 文字颜色
                    font_size=20
                ),
            )
        )
            .reversal_axis()
    )
    #
    pie_data = [[x[0], x[1]] for x in map_data]
    pie_data = sorted(pie_data,key=lambda x:x[1],reverse=True)

    if len(pie_data) > 20:
        pie_data_2=pie_data[-10:]
        pie_data = pie_data[0:10]
        others=[]
        others.append('其他城市')
        others_value=0
        for item in pie_data_2:
            others_value = others_value + item[1]
        others.append(others_value)
        pie_data.append(others)

    if pie_data[len(pie_data)-1][0]=="null":
        pie_data = pie_data[0:len(pie_data)-1]
    pie_chart = (
        Pie()
            .add(
            series_name="",
            data_pair=pie_data,
            radius=["15%", "30%"],  # 半径大小，相对整个画布高宽的一半，数组的第一项是内半径，第二项是外半径
            center=["17%", "55%"],  # 位置，相对整个画布来说
            itemstyle_opts=opts.ItemStyleOpts(
                border_width=1, border_color="white"
            ),
            label_opts=opts.LabelOpts(
                font_size=10,
                font_weight='bold',
                position='right',
            )
        )
            .set_global_opts(
            tooltip_opts=opts.TooltipOpts(is_show=True, formatter="{b} {d}%"),  # 显示的格式
            legend_opts=opts.LegendOpts(is_show=False),
        )
    )
    pie2_data = [[x[0], x[1]] for x in map_data]
    pie2_data = sorted(pie2_data,key=lambda x:x[1],reverse=True)
    mainland_data = []
    mainland_data.append('大陆')

    mc_data = []
    mc_data.append('中国的澳门')
    hk_data = []
    hk_data.append('中国的香港')

    tw_data = []
    tw_data.append('中国的台湾')

    print(pie2_data)
    print(mainland_data)
    print(hk_data)
    print(tw_data)
    print(mc_data)

    tw_data_value = 0
    mc_data_value = 0
    mainland_data_value = 0
    hk_data_value = 0
    for item in pie2_data:
        if item[0]=="台湾" :
            tw_data_value = tw_data_value + item[1]
        elif item[0]=="香港":
            hk_data_value = hk_data_value + item[1]
        elif item[0]=="澳门":
            mc_data_value = mc_data_value + item[1]
        else:
            mainland_data_value = mainland_data_value+item[1]
    mainland_data.append(mainland_data_value)
    hk_data.append(hk_data_value)
    tw_data.append(tw_data_value)
    mc_data.append(mc_data_value)
    pie2_data_used = []
    pie2_data_used.append(mainland_data)
    pie2_data_used.append(hk_data)
    pie2_data_used.append(tw_data)
    pie2_data_used.append(mc_data)
    print(pie2_data_used)
    pie2_chart = (
        Pie()
            .add(
            series_name="",
            data_pair=pie2_data_used,
            radius=["15%", "30%"],  # 半径大小，相对整个画布高宽的一半，数组的第一项是内半径，第二项是外半径
            center=["82%", "55%"],  # 位置，相对整个画布来说
            itemstyle_opts=opts.ItemStyleOpts(
                border_width=1, border_color="white"
            ),
            label_opts=opts.LabelOpts(
                font_size=10,
                font_weight='bold',
                position='right',
                formatter="{b}: {c}",
            )
        )
            .set_global_opts(
            tooltip_opts=opts.TooltipOpts(is_show=True, formatter="{b} {d}%"),  # 显示的格式
            legend_opts=opts.LegendOpts(is_show=False),
        )
    )



    # 组合图表
    grid_chart = (
        Grid()
            .add(bar_chart, grid_opts=opts.GridOpts(pos_left="20", pos_right="30%", pos_top="80%", pos_bottom="5"))
            .add(pie_chart, grid_opts=opts.GridOpts())
            .add(map_chart, grid_opts=opts.GridOpts())
            .add(pie2_chart, grid_opts=opts.GridOpts())
    )
    return grid_chart


def _timeline():
    path = "../../../../../resources/static"
    files = os.listdir(path)
    for file in files:
        if file == "2022-03-13ConfirmData.json":
            break
        date = file[0:10]
        get_data(date)

    time_list = []
    for file in files:
        if file == "2022-03-13ConfirmData.json":
            break
        date = file[0:10]
        time_list.append(date)
    timeline = Timeline(    # 设置画布大小，主题
        init_opts=opts.InitOpts(width="1330px", height="600px", theme=ThemeType.PURPLE_PASSION)
    )
    for date in time_list:
        charts = covid_19_charts(date)  # 获取图表
        timeline.add(charts, time_point=str(date))

    timeline.add_schema(
        orient="vertical",
        is_auto_play=True,
        is_inverse=True,    # 反向
        play_interval=350,
        pos_left="null",
        pos_right="5",
        pos_top="20%",
        pos_bottom="20%",
        width="50",
        label_opts=opts.LabelOpts(is_show=False),
        is_timeline_show=False
    )
    return timeline


if __name__ == '__main__':
    timeline = _timeline()
    timeline.render('Covid_19Charts.html')