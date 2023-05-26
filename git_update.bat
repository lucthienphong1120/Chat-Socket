@echo off

REM Step 1: Git pull
echo [!] Pull the lastest news from Github...
git pull
echo .

REM Check if git pull encountered an error
if errorlevel 1 (
    color 4
    echo [!] Git pull failed. Please resolve conflicts manually.
    echo .
    pause
    exit
)

REM Step 2: Git add -A
echo [!] Add current change to git...
git add -A
echo .

REM Step 3: User input for commit message
set /p message="[!] Enter commit message: "
git commit -m "%message%"
echo .

REM Step 4: Git push
color 2
echo [!] Push all changes to Github...
git push
echo .

REM End of script
pause
