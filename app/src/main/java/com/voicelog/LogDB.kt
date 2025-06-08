package com.voicelog

data class LogEntry(
    val name: String,
    val phone: String?,
    val timestamp: Long
)

class LogDB {
    private val logs = mutableListOf<LogEntry>()

    fun addLogEntry(name: String, phone: String?, timestamp: Long) {
        logs.add(LogEntry(name, phone, timestamp))
    }

    fun allLogs(): List<LogEntry> = logs.toList()
}
