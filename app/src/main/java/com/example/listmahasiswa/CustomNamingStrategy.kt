package com.example.listmahasiswa

import com.google.gson.FieldNamingPolicy
import com.google.gson.FieldNamingStrategy
import java.lang.reflect.Field

object CustomNamingStrategy : FieldNamingStrategy
{
    override fun translateName(f: Field): String
    {
        return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(f)
    }
}