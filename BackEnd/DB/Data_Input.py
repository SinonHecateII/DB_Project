import ImportantData
import requests, json

def readDatabase(databaseId, headers):
    readUrl = f"https://api.notion.com/v1/databases/{databaseId}/query"

    res = requests.post(readUrl, headers = headers)
    print(res.status_code)

    data = res.json()

    for result in data['results']:
        try:
            properties = result['properties']
            Restaurant_Name = properties['가게명']['title'][0]['text']['content']
            Restaurant_Vibe = properties['분위기']['multi_select'][0]['name']
            Restaurant_Loc = properties['위치']['multi_select'][0]['name']
            print(f'가게이름: {Restaurant_Name}, 분위기: {Restaurant_Vibe}, 위치: {Restaurant_Loc}')
        except:
            continue

token = ImportantData.NotionToken
databaseId = ImportantData.NotionTableID

headers = {
    "Authorization": f"Bearer {token}",
    "Notion-Version": "2022-02-22",
}

readDatabase(databaseId, headers)

