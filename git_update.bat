@echo off
color 2

REM Step 1: Git pull
echo Pull the lastest news from Github...
git pull

REM Check if git pull encountered an error
if errorlevel 1 (
    echo Git pull failed. Please resolve conflicts manually.
    pause
    exit
)

REM Step 2: Git add -A
echo Add current change to git
git add -A

REM Step 3: User input for commit message
set /p message="Enter commit message: "
git commit -m "%message%"

REM Step 4: Git push
echo Push all changes to Github...
git push

REM End of script
pause
