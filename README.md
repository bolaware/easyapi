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

##  Help
* Find a bug? [Open an issue](https://github.com/BolajisBrain/easyapi/issues)
* Want to contribute? [Submit a pull request](https://help.github.com/articles/creating-a-pull-request).

## License
```
Rave's Android DropIn UI
MIT License

Copyright (c) 2017

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```