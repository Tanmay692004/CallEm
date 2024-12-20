# Emergency Contact Macro App

## Overview
The **Emergency Contact Macro** app is designed to assist users in quickly contacting emergency services and sending distress messages to their selected contacts during emergencies. The app allows for emergency calls, SMS notifications, live location sharing, and optional audio recording, all triggered by a specific button combination on Android devices. This app is ideal for individuals in unsafe environments, elderly users, or travelers who need an easy and efficient way to seek help.

## Product Perspective

The app is intended to operate as a standalone product on Android devices. It will run in the background and will be triggered by specific user input, that is three consecutive clicks on the power button and the volume up button for launch confirmation. The reason for picking this particular set of input clicks is due to its ease of doing so and also this input combination does not serve any built-in function in any android Device. The app will integrate with native phone functions (call, SMS, location) and launch a built-in voice recorder

## Features
- **Emergency Call**: Automatically places a call to emergency services or predefined emergency contacts.
- **SMS to Contacts**: Sends a predefined SMS to multiple emergency contacts with a distress message and live location (if enabled).
- **Location Sharing**: Shares the user’s live location via Google Maps to selected contacts (requires location permission).
- **Audio Recording**: Optionally records audio for evidence collection, saved locally on the device.
- **Notification Interface**: Provides an easy-to-use notification to stop location sharing, stop audio recording, and send a "I’m safe now" message.

## Prerequisites
- Android device with **Android 5.1** or higher.
- Permissions for **SMS**, **Phone Call**, **Location**, and **Microphone** must be granted.
- Active mobile network for calls, SMS, and location sharing.

## Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Tanmay692004/CallEm.git
