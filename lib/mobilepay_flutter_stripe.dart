import 'package:flutter/services.dart';

class MobilePayFlutterStripe {
  static const MethodChannel _channel =
      MethodChannel('mobilepay_flutter_stripe');

  Future<String> startCheckout(
      String paymentIntentClientSecret, String publishableKey) async {
    final String result = await _channel.invokeMethod('startCheckout', {
      'paymentIntentClientSecret': paymentIntentClientSecret,
      'publishableKey': publishableKey,
    });
    return result;
  }
}
