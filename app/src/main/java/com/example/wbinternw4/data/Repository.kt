package com.example.wbinternw4.data

import com.github.javafaker.Faker

class Repository {
    fun getData(): List<Chat> {
        var chats = mutableListOf<Chat>()
        val faker = Faker.instance()
        IMAGES.shuffle()
        DATE.shuffle()
        for (i in 0..10) {
            chats.add(
                Chat(
                    i,
                    IMAGES[i % IMAGES.size],
                    faker.name().fullName(),
                    DATE[i % DATE.size],
                )
            )
        }
        return chats
    }

    fun getMessage(): ArrayList<MessageData> {
        val messageData = arrayListOf<MessageData>()
        val faker = Faker.instance()
        for (i in 0..20) {
            messageData.add(
                MessageData(
                    i,
                    (0..1).random(),
                    faker.company().name()
                )
            )
        }
        return messageData
    }
}