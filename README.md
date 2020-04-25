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
View -> Tool Windows -> Maven Projects
``` 
Maven Projects -> Profiles
```    
чекаем intellij-javadoc-fix  

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
On windows 10 in powershell  
```
sh install-properties.sh
```

### 2. git workflow

1\. Основная ветка разработки - dev  
2\. Для своего кода делаем отдельную ветку, ответвляясь от dev  

```
git checkout dev
git checkout -b dev_имясвоейветки
```

3\. Для своего кода делаем unit-тесты
(За осонову брать уже сделанные для модуля core)  
4\. Перед тем, как делать pull request запускаем тесты.
```
mvn test
```
У кого не установлен maven запускаем test  
View -> Tool Windows -> Maven Projects
```   
cms-backend -> Lifecycle -> test
``` 
5\. Если упали свои или чужие тесты - исправляем свой код, 
который что-то сломал.

6\. После того, как код принят и слит с dev, удаляем свою ветку локально
(на удаленном репозитории удалять свою ветку не надо), 
возвращаясь к пункту 1\. 
