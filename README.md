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
```
Далее открываем проект utils в Intellij Idea  
Идем в правый toolbar
``` 
Maven Projects -> Profiles
```    
чекаем intellij-javadoc-fix
```   
utils -> Lifecycle -> install
``` 
Запускаем install (либо кнопка вверху, либо через контекстное меню)                   


### 2. Setup postgres
Read  database/readme.txt 


### 3. Setup profiles
Копируем профили
```
./install-properties.sh
```
On windows 10 in powershell
```
sh install-properties.sh
```
(не проверялось)