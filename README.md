# CircleSeekBar
Custom View. The circle seek bar.

## Download

Gradle:
```groovy
compile 'ru.bullyboo.view:circleseekbar:1.0.2'
```

Maven:
```xml
<dependency>
  <groupId>ru.bullyboo.view</groupId>
  <artifactId>circleseekbar</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
```

## Donations

Please support the project! 

For coffee/beer and further development of the project:

* [Donate 5$](https://www.paypal.me/bullyboo/5usd)
* [Donate 10$](https://www.paypal.me/bullyboo/10usd)
* [Donate 25$](https://www.paypal.me/bullyboo/25usd)
* [Donate 50$](https://www.paypal.me/bullyboo/50usd)
* [Donate 100$](https://www.paypal.me/bullyboo/100usd)
* [Other](https://www.paypal.me/bullyboo)

## About

The Circle Seek Bar is a custom view. But it's not a simple view.

![alt text](https://github.com/BullyBoo/CircleSeekBar/blob/master/screenshots/Screenshot_2.png)
  
It's extends by `FrameLayout`, so if you need, you can add some views to inside of Circle Seek Bar.

![alt text](https://github.com/BullyBoo/CircleSeekBar/blob/master/screenshots/Screenshot_3.png)

## Usage

For add this view to your layout, you shold to add:
```xml
<ru.bullyboo.view.CircleSeekBar
                android:layout_width="300dp"
                android:layout_height="300dp"/>
```

For customization this view you need to override some properties.
The list of all custom properties:  
  
**backgroundCircleLineWidth** - the width of circle line on the background of circle seek bar.   
This value has format `dimension`.  
Default value is `2dp`.    
  
**progressCircleLineWidth** - the width of arc line, which is mean a progress of circle seek bar  
This value has format `dimension`.  
Default value is `2dp`.   
  
**backgroundCircleLineColor** - the color of circle on the background of circle seek bar  
This value has format `color`.  
Default color is - ![#ebebed](https://placehold.it/15/ebebed/000000?text=+) `#ebebed`
  
**progressCircleLineColor** - the color of arc, which is mean a progrss of circle seek bar  
This value has format `color`.  
Default color is - ![#007eff](https://placehold.it/15/007eff/000000?text=+) `#007eff`
   
**dotRadius** - the radius of dot, what will change the position with value  
This value has format `dimension`.  
Default value is `19dp`.   
  
**dotColor** - the color of dot  
This value has format `color`.  
Default color is - ![#007eff](https://placehold.it/15/007eff/000000?text=+) `#007eff`
   
**minValue** - minimum value of circle seek bar. Be careful, this value must be `int`, and can't be lager then maximim value.  
If the `minValue` will be lager then `maxValue`, `minValue` will set to value equals of `maxValue`.  
This value has format `integer`.  
Default value is `0`.
  
**maxValue** - maximum value of circle seek bar. Be careful, this value must be `int`, and can't be less then minimum value.  
This value has format `integer`.  
Default value is `100`.
  
**value** - the value of circle seek bar. Be careful, this value must be `int`, and can't be less then minimum value and lager of maximim value.  
If `value` will be larger of `maxValue` or less of `minValue`, `value` will set to value equals of `minVakue`.
This value has format `integer`.  
Default value is `30`.
  
**textAppearance** - the appereance of text, witch will added in `onLayout`. It's a default textView in that you will see the currenct value of circle seek bar.  
This value has format `reference`.  
Default value is `@style/CircleProgressViewStyle.TextAppearance`.  
  
**showCounter** - the flag show/hide counter in th center of circle seek bar.  
This value has format `boolean`.  
Default value is `true`.

**isClockwise** - the flag for setting the direction of circle seek bar.
This value has format `boolean`.  
Default value is `true`.
  
## CallBacks
The Cirlce Seek Bar has two own callbacks.  
The first is `CircleSeekBar.Callback` with a methods: 
  
```java
void onStartScrolling(int startValue)  
void onEndScrolling(int endValue)  
```
`onStartScrolling` is calling when user down the finger to the arc of Circle Seek Bar.  
`onEndScrolling` is calling when user up the finger from Circle Seek Bar.

Also it's has a second callback - `OnValueChangedListener`  
It's has only one method:  
```java
void onValueChanged(int value);
```
  
`onValueChanged` is calling always, when the value of Circle Seek Bar was changed.

## License
```
Copyright (C) 2017 BullyBoo

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ```
