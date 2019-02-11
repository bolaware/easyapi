# easyapi
EasyApi is an android tool to easily make network request without the fuss of setting up a REST client.

## Make a GET request
Heres an example of getting news articles from a public api at [newsapi.org](https://newsapi.org)

    new EasyApiCaller(this)
                .setMethod(GET)
                .setTimeOut(1)
                .showLogResponse(true)
                .setUrl("https://newsapi.org/v2/everything?q=bitcoin&from=2019-02-05&sortBy=publishedAt&apiKey=8d1024a54b9b473e930770f97189febe")
                .request()
                .await(
                    new Callbacks.onResponse(){
                        @Override
                        public void onResponse(@NotNull String jsonAsString, @NotNull EasyApiCaller easyApiCaller) {
                            // convert to your own class, for example in this case `NewsResponse`
                            NewsResponse newsResponse = EasyApiUtils.convertFromJson(jsonAsString, NewsResponse.class);
                            Toast.makeText(JavaMainActivity.this, newsResponse.getArticles().size() + " articles fetched", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(@Nullable String errorResponse, int responseCode) {
                            textView.setText(errorResponse);
                        }
                    }
                );

## Make a POST request
Here's another example of a post request to create an employee using a public api at [reqres.in](https://reqres.in)

        try {
            JSONObject obj = new JSONObject();
            obj.put("name", "John Doe");
            obj.put("job", "Engineer");

            new EasyApiCaller(this)
                    .setMethod(POST, obj)
                    .setTimeOut(1)
                    .showLogResponse(true)
                    .setUrl("https://reqres.in/api/users")
                    .request()
                    .await(
                        new Callbacks.onResponse(){
                            @Override
                            public void onResponse(@NotNull String jsonAsString, @NotNull EasyApiCaller easyApiCaller) {
                                final TextView textView = (TextView) findViewById(R.id.textTV);
                                textView.setText(jsonAsString);
                            }

                            @Override
                            public void onFailure(@Nullable String errorResponse, int responseCode) {
                                textView.setText(errorResponse);
                            }
                        }
                    );
        } catch (JSONException e){
            e.printStackTrace();
        }

## Adding it to your project
**Step 1.** Add it in your root build.gradle at the end of repositories:

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2.** Add the dependency

    dependencies {
	      implementation 'com.github.BolajisBrain:easyapi:v1.1'
	}


##  Methods
| function        | parameter           | type | required  |
| ------------- |:-------------:| -----:| -----:|
| setMethod(method)      |  Set request methods with `post` or `get` | `String` | Required
| setUrl(url)      |  Set full url as a string | `String` | Required
| setTimeOut(timeOut)      |  Set time out response in minutes. Default is one minute | `Long` | Optional
| showLogResponse(show)      |  Display requests in logcat. Default is false | `Boolean` | Optional
| request()      |  Make network request |  | Required
| await(callback)      |  Call to get result of network request | `Callback` | Required
| EasyApiUtils.convertFromJson(jsonAsString, class) |  Call to convert json string to a corresponding java class | `jsonString`, `Class` | Optional

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