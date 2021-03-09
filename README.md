# A project of Soccer League
  Imagine that, we want to organize a soccer league in a company. To manage league matches,
we want to develop an android application. This application will be used to determine match
fixture and needs to have below functionalities;
1. Query team names from Web Service by REST API,
2. Determine fixture for 2 half of league matches,
3. Show team list and draw fixture button on the main screen of an application,
4. After a user click to Draw Fixture button, show each week matches on swipeable UI (when
we swipe from left to right it should show the previous week when we swipe from right to left
it should show next week matches)

## Important Note
I cannot find any free API for query team names from Web Service. Then, i decided for use json-server.
This library provide create local fake server. You can reach more information from link. 

json-server link : https://github.com/typicode/json-server

## Quick installation for json-server

For install:
```console
npm install -g json-server
```
For localhost test:
```console
json-server --watch teams.json --port 8000
```
For fake server over wifi:
(Change XXX with your local IP)

```console
json-server --host 192.168.1.XXX teams.json --port 8000
```
## After installation json-server

For testing change 3 // TODO in codes. Change BASE_URL, SerializedNames and path informations
according to your API or your file which is published with json-server.


# teams.json
My test data for this application. This data contains TFF Super League Teams. Copy-paste your json file and
publish with json-server.

```yaml
{
  "teams": [
    { "id": 1, "name": "ATAKAŞ HATAYSPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000122_120x120.png" },
    { "id": 2, "name": "AYTEMİZ ALANYASPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000050_120x120.png" },
    { "id": 3, "name": "BEŞİKTAŞ A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000148_120x120.png" },
    { "id": 4, "name": "BÜYÜKŞEHİR BELEDİYE ERZURUMSPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/010491_120x120.png" },
    { "id": 5, "name": "ÇAYKUR RİZESPOR A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000189_120x120.png" },
    { "id": 6, "name": "DEMİR GRUP SİVASSPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000073_120x120.png" },
    { "id": 7, "name": "FATİH KARAGÜMRÜK A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000169_120x120.png" },
    { "id": 8, "name": "FENERBAHÇE A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000150_120x120.png" },
    { "id": 9, "name": "FRAPORT-TAV ANTALYASPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000051_120x120.png" },
    { "id": 10, "name": "GALATASARAY A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000162_120x120.png" },
    { "id": 11, "name": "GAZİANTEP FUTBOL KULÜBÜ A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000251_120x120.png" },
    { "id": 12, "name": "GENÇLERBİRLİĞİ", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000164_120x120.png" },
    { "id": 13, "name": "GÖZTEPE A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000269_120x120.png" },
    { "id": 14, "name": "HES KABLO KAYSERİSPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000071_120x120.png" },
    { "id": 15, "name": "İTTİFAK HOLDİNG KONYASPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000158_120x120.png" },
    { "id": 16, "name": "KASIMPAŞA A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000038_120x120.png" },
    { "id": 17, "name": "MEDİPOL BAŞAKŞEHİR FK", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000240_120x120.png" },
    { "id": 18, "name": "MKE ANKARAGÜCÜ", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000153_120x120.png" },
    { "id": 19, "name": "TRABZONSPOR A.Ş.", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000154_120x120.png" },
    { "id": 20, "name": "YENİ MALATYASPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/011385_120x120.png" },
    { "id": 21, "name": "YUKATEL DENİZLİSPOR", "photo": "https://fys.tff.org/TFFUploadFolder/KulupLogolari/000080_120x120.png" }
  ]
}
```

Best regards,
Have a good day.
