package org.dsqrwym.homechat

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform