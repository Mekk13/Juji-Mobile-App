import 'package:flutter/material.dart';

import 'data/move_repository.dart';
import 'ui/move_list_screen.dart';
import 'viewmodels/move_list_view_model.dart';

void main() {
  final repository = InMemoryMoveRepository();
  final viewModel = MoveListViewModel(repository);
  runApp(MainApp(viewModel: viewModel));
}

class MainApp extends StatelessWidget {
  final MoveListViewModel viewModel;

  const MainApp({super.key, required this.viewModel});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: MoveListScreen(viewModel: viewModel),
    );
  }
}
