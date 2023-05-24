@echo off

REM Step 1: Git pull
git pull

REM Check if git pull encountered an error
if errorlevel 1 (
    echo Git pull failed. Please resolve conflicts manually.
    pause
    exit
)

REM Step 2: Git add -A
git add -A

REM Step 3: User input for commit message
set /p message="Enter commit message: "

REM Step 4: Git commit
git commit -m "%message%"

REM Step 5: Git push
git push

REM End of script
