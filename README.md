# cms_backend

## Installation

### 1. Install dependencies 
```
git clone git@github.com:dreamworkerln/utils.git
cd utils
mvn -DskipTests clean install
```

У кого не установлен ключ RSA для ssh и не установлен maven  
```
git clone https://github.com/dreamworkerln/utils.git
cd utils
mvnw -DskipTests clean install
```
Либо открываем проект utils в Intellij Idea  
View -> Tool Windows -> Maven Projects
``` 
Maven Projects -> Profiles
```    
чекаем intellij-javadoc-fix (Для Intellij Idea 2019.3 и выше не надо) 

Далее 
```   
utils -> Lifecycle -> install
``` 
Запускаем install (либо кнопка вверху, либо через контекстное меню)                   


### 2. Setup postgres
Read  infrastructure/database/readme.txt 


### 3. Setup profiles
Выполняем (копируются профили)
```
./install-properties.sh
```
В windows 10 в PowerShell  
```
sh install-properties.sh
```

### 2. git workflow

1\. Основная ветка разработки - dev
<br>

2\. Для своего кода делаем отдельную ветку, ответвляясь от dev  
```
git checkout dev
git checkout -b dev_имясвоейветки
```
<br>

3\. Делаем unit-тесты.
(За основу брать уже сделанные для модуля core-services, не забываем подключить .properties -
см CorePropertiesConfiguration.java)
<br> 
 
4\. Перед тем, как делать pull request запускаем тесты.
```
mvn test
``` 
У кого отдельно не установлен maven запускаем интегрированный mvnw
```
mvnw test
```
<br>

5\. Если упали свои или чужие тесты - исправляем свой код, 
который что-то сломал.
<br>

6\. Запускаем Analyse -> Inspect code. Если есть ошибки в java
(Probable bugs), то исправляем, и не заглушками SuppressWarnings, 
а проверкой на null, валидацией переданных параметров, 
киданием исключения(если это требуется по логике).<br><br>
Сразу маргинальные варианты:
``` 
catch (Exception e) {
    e.printStackTrace()
}

или

catch (InterruptedException e) {} 
```
<br>

7\. Отправляем свою ветку в репозиторий.
<br>

8\. Идем в jenkins, смотрим результаты сборки по вашему коммиту.
Если сборка провалилась, см пункт 4.
<br>

9\. После того, как код принят и слит с dev, удаляем свою ветку локально
(на удаленном репозитории удалять свою ветку не надо), 
возвращаясь к пункту 1\.
<br>
<br>

Кто считает себя норм в git, может не пересоздавать свою ветку, 
а периодически сливать изменения из dev в свою ветку
```
git fetch
git checkout dev_имясвоейветки
git merge dev
```
При этом конфликты слияния будете разруливать самостоятельно. 
