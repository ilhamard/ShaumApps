package com.example.shaumapps.data.remote.response

import com.google.gson.annotations.SerializedName

data class HadisResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("error")
	val error: Boolean
)

data class Data(

	@field:SerializedName("contents")
	val contents: Contents,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("available")
	val available: Int,

	@field:SerializedName("id")
	val id: String
)

data class Contents(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("arab")
	val arab: String
)
