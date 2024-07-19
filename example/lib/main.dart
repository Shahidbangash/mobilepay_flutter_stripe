import 'package:flutter/material.dart';
import 'package:mobilepay_flutter_stripe/mobilepay_flutter_stripe.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('MobilePay Example'),
        ),
        body: Center(
          child: CheckoutButton(),
        ),
      ),
    );
  }
}

class CheckoutButton extends StatefulWidget {
  @override
  _CheckoutButtonState createState() => _CheckoutButtonState();
}

class _CheckoutButtonState extends State<CheckoutButton> {
  final MobilePayFlutterStripe _mobilePayFlutterStripe =
      MobilePayFlutterStripe();

  void _startCheckout() async {
    String paymentIntentClientSecret = "your_payment_intent_client_secret";
    String publishableKey = "your_publishable_key";
    try {
      String result = await _mobilePayFlutterStripe.startCheckout(
          paymentIntentClientSecret, publishableKey);
      print(result);
    } catch (e) {
      print("Error: $e");
    }
  }

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: _startCheckout,
      child: Text('Start Checkout'),
    );
  }
}
