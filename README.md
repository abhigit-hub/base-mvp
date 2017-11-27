# Android Architecture : base-mvp


Inspiration mostly from the good works of [Mindorks Open Source Project](https://github.com/MindorksOpenSource/android-mvp-architecture). But the app is tweaked a little on the navigation front and also houses a few more features, compared to the original app.

This repo is a sample application that implements **MVP** architecture using **RxJava2**, **Dagger2**, **Retrofit2**, **Room** and **Butterknife**.
We use Retrofit and Room library for network calls and database operations respectively. _(Original App uses FastAndroidNetworking and GreenDao)_

## Topics
- What does this app do?
- Quick Glimpse
- Naive Attempt at Animated Vector Drawable
- APIs and Architectures Used
- Library reference resources

## What does this app do?
To sum up in one line, this app shows news feed(Open Source codes and Developer Blogs) to the consumer. 
Now, the idea is essentially the same but the manner in which it is served to the Consumer, differs(precisely 3).

## Quick Glimpse



## APIs and Architectures Used

1. Model View Presenter Architecture
1. RxJava/RxAndroid 2 for concurrency
1. Dagger2 for managing the dependency tree and also for dependency injection
1. Room library from Google's Architecture Components, for database operation
1. Retrofit library for network calls
1. ButterKnife for View Injections
1. Glide for Image Loading


I'm also looking at adding the following Libraries for future iterations:

1. LeakCanary
1. Mockito
1. JUnit
1. Bottom Sheets




## Library reference resources
1. RxJava2: https://blog.kaush.co/2017/06/21/rxjava1-rxjava2-migration-understanding-changes/

