package com.arch.jonnyhsia.memories.model.passport.bean

sealed class LoginType {

    class Illegal : LoginType() {
        override fun equals(other: Any?): Boolean {
            return other is Illegal
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }

    class LoginDirectly(val username: String) : LoginType() {
        override fun equals(other: Any?): Boolean {
            return other is LoginDirectly &&
                    this.username == other.username
        }

        override fun hashCode(): Int {
            return username.hashCode()
        }
    }

    class LoginWithPassword : LoginType() {
        override fun equals(other: Any?): Boolean {
            return other is LoginWithPassword
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }

    class Register : LoginType() {
        override fun equals(other: Any?): Boolean {
            return other is Register
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }
}