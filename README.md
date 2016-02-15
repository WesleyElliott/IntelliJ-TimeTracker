# IntelliJ-TimeTracker
A simple time tracker plugin for IntelliJ platforms that logs time worked on a specific Git or Mercurial branch

## Usage
The timer is located in the bottom status bar, on the far right. It displays the time in an HH:mm:ss format.
To start / resume the timer, either select the option from `Tools`->`Time Tracker`->`Start Logging` or use the keyboard shortcut
`Ctrl Cmd 1` (OS X) / `Ctrl Alt 1` (Windows)

To pause the timer, go to `Tools`->`Time Tracker`->`Stop Logging` or use the keyboard shortcut `Ctrl Cmd 2` (OS X) / `Ctrl Alt 2` (Windows)

The timer can be paused and resumed as and when needed. When the task is done, the timer can be stopped by either selecting `Tools`->`Time Tracker`->`Finish Task` or use the keyboard shortcut `Ctrl Cmd 3` (OS X) / `Ctrl Alt 3` (Windows)

## Project History
Time Tracker keeps a history of the times saved when finished tasks. To view this history, click on the timer icon on the bottom status bar. Values can be editted by double clicking on a cell. These changes are saved when `Enter` is pressed. History can also be cleared with the `Clear History` button

## Future Improvments
 - Exporting history
 - Grouping tasks times in history
 - Grouping days in history
 - Auto stop and save when branch changes
