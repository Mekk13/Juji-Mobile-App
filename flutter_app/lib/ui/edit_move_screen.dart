import 'package:flutter/material.dart';

import '../viewmodels/move_list_view_model.dart';
import 'widgets/move_form.dart';

class EditMoveScreen extends StatelessWidget {
  final MoveListViewModel viewModel;
  final int moveId;

  const EditMoveScreen({super.key, required this.viewModel, required this.moveId});

  @override
  Widget build(BuildContext context) {
    final move = viewModel.getById(moveId);
    return Scaffold(
      appBar: AppBar(title: const Text('Edit move')),
      body: move == null
          ? const Center(child: Text('Move not found'))
          : MoveForm(
              initial: move,
              submitLabel: 'Save',
              onSubmit: (updated) {
                viewModel.update(updated);
                Navigator.pop(context);
              },
              onCancel: () => Navigator.pop(context),
            ),
    );
  }
}
