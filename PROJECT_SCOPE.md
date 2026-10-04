# My Messages — Product Scope

## Rule
Version 1 must never have fewer features than the current working app. Existing working features remain while V1 is expanded and polished.

## V1 — Complete before APK
### Home / Inbox
- All SMS conversations
- Contact name/number
- Latest message preview
- Unread count and unread section
- Pin / Archive / Blocked
- Swipe left delete
- Swipe right archive
- Search by name, number, message
- Long-press conversation actions

### Conversation
- Incoming/outgoing messages
- Timestamp
- Delivery status
- Long SMS / multipart SMS
- SIM selection
- Copy / Forward / Delete
- Multi-message selection and bulk delete
- Call contact/number
- Send with Enter option
- Attachment picker foundation kept without pretending MMS is complete

### Contacts
- Automatic contact name lookup
- Unknown number fallback
- Call action

### Notifications
- Sender
- Preview / hidden preview
- Direct reply
- Mark read
- Clear
- Sound / vibration preferences
- OTP recognition

### Backup
- Manual JSON export
- JSON restore
- Automatic daily local backup
- Backup history/date/time
- Automatic backups never overwrite exported files

### Privacy
- PIN app lock
- Biometric/fingerprint unlock where supported
- Hide notification preview
- Local/offline-first data

### Settings
- Default SMS app
- Delivery reports preference
- Send with Enter
- SIM preference
- Notification sound/vibration/preview
- App lock and biometric
- Light/dark appearance
- Automatic backup
- Backup & Restore

### Organization
- Pinned
- Unread
- Archived
- Blocked
- Labels
- Statistics
- Cleanup

## V2 — Advanced
Only after V1 is complete and tested:
- True MMS send/receive
- Images/videos/documents inside conversations
- Cloud/Google Drive sync
- Cross-device restore
- Favorites
- Advanced filters
- Smart OTP
- Transactional/promotional categorization
- Advanced statistics
- RCS/alternative messaging transport where Android/device/carrier support permits

## Non-regression rule
No V2 work may remove or weaken any V1/current feature.
