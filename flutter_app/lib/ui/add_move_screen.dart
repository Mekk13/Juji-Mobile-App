import 'package:flutter/material.dart';

import '../viewmodels/move_list_view_model.dart';
import 'widgets/move_form.dart';

class AddMoveScreen extends StatelessWidget {
  final MoveListViewModel viewModel;

  const AddMoveScreen({super.key, required this.viewModel});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Add move')),
      body: MoveForm(
        submitLabel: 'Add',
        onSubmit: (move) {
          viewModel.add(move);
          Navigator.pop(context);
        },
        onCancel: () => Navigator.pop(context),
      ),
    );
  }
}
