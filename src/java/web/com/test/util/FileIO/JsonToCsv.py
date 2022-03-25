#使用python来把json格式的数据转化为csv
import csv
import json
import os
def tranform(fileName):
    jsonFile = open('../../../../../../resources/static/' + fileName+'.json','r',encoding='utf-8')
    csvfile = open("../../../../../../resources/csvData/"+fileName+".csv",'w',encoding='utf-8',newline='')
    jsonList = json.load(jsonFile)
    key = jsonList[0].keys()
    value = [item.values() for item in jsonList]

    csvWriter = csv.writer(csvfile)
    csvWriter.writerow(key)
    csvWriter.writerows(value)

    csvfile.close()
    jsonFile.close()
if __name__ == "__main__":
    path = "../../../../../../resources/static"
    files = os.listdir(path)
    for file in files:
        if file == "2022-03-13ConfirmData.json":
            break
        fileName = file.split('.')[0]
        print(fileName)
        tranform(fileName)
