# easyapi
EasyApi is an android tool to easily make network request without the fuss of setting up a REST client.

## Make a GET request
Heres an example of getting news response from a public api at [newsapi.org](https://newsapi.org)

    EasyApiCaller(this)
                .method(GET)
                .timeOut(1)
                .logResponse(true)
                .url("https://newsapi.org/v2/everything?q=bitcoin&from=2019-02-05&sortBy=publishedAt&apiKey=8d1024a54b9b473e930770f97189febe")
                .request()
                .await(object : Callbacks.onResponse {
                    override fun onResponse(jsonAsString: String, easyApiCaller: EasyApiCaller) {
                        newsResponse = easyApiCaller.convertFromJSON<NewsResponse>(jsonAsString)
                    }

                    override fun onFailure(errorResponse: String?, responseCode: Int) {
                        textTV.text = errorResponse
                    }
                })

## Make a POST request
Here's another example of a post request to create an employee using a public api at [reqres.in](https://reqres.in)

    val obj = JSONObject()
        obj.put("name", "John")
        obj.put("job", "Engineer")

        EasyApiCaller(this)
                .method(POST, obj)
                .timeOut(1)
                .logResponse(true)
                .url("https://reqres.in/api/users")
                .request()
                .await(object : Callbacks.onResponse {
                    override fun onResponse(jsonAsString: String, easyApiCaller: EasyApiCaller) {
                        textTV.text = jsonAsString
                    }

                    override fun onFailure(errorResponse: String?, responseCode: Int) {
                        textTV.text = errorResponse
                    }
                })
    }
