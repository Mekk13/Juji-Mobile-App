import 'package:flutter/material.dart';

import '../models/move.dart';
import '../viewmodels/move_list_view_model.dart';
import 'add_move_screen.dart';
import 'edit_move_screen.dart';

class MoveListScreen extends StatelessWidget {
  final MoveListViewModel viewModel;

  const MoveListScreen({super.key, required this.viewModel});

  Future<void> _confirmDelete(BuildContext context, Move move) async {
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Delete move?'),
        content: Text('Are you sure you want to delete "${move.name}"?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context, false),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () => Navigator.pop(context, true),
            child: const Text('Delete'),
          ),
        ],
      ),
    );
    if (confirmed == true) {
      viewModel.delete(move.id);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Move log')),
      floatingActionButton: FloatingActionButton(
        onPressed: () => Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => AddMoveScreen(viewModel: viewModel)),
        ),
        child: const Icon(Icons.add),
      ),
      body: ListenableBuilder(
        listenable: viewModel,
        builder: (context, child) {
          final moves = viewModel.moves;
          return ListView.builder(
            itemCount: moves.length,
            itemBuilder: (context, index) {
              final move = moves[index];
              return MoveListItem(
                key: ValueKey(move.id),
                move: move,
                onTap: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => EditMoveScreen(viewModel: viewModel, moveId: move.id),
                  ),
                ),
                onDelete: () => _confirmDelete(context, move),
              );
            },
          );
        },
      ),
    );
  }
}

class MoveListItem extends StatelessWidget {
  final Move move;
  final VoidCallback onTap;
  final VoidCallback onDelete;

  const MoveListItem({
    super.key,
    required this.move,
    required this.onTap,
    required this.onDelete,
  });

  @override
  Widget build(BuildContext context) {
    final proficiency = move.proficiency;
    return ListTile(
      onTap: onTap,
      title: Text(move.name),
      subtitle: Text(
        proficiency != null ? '${move.category} · $proficiency/10' : move.category,
      ),
      trailing: IconButton(
        icon: const Icon(Icons.delete),
        onPressed: onDelete,
      ),
    );
  }
}
