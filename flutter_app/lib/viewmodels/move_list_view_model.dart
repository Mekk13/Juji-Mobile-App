import 'package:flutter/foundation.dart';

import '../data/move_repository.dart';
import '../models/move.dart';

class MoveListViewModel extends ChangeNotifier {
  final MoveRepository _repository;
  final List<Move> _moves;

  MoveListViewModel(this._repository) : _moves = List.of(_repository.getAll());

  List<Move> get moves => List.unmodifiable(_moves);

  Move? getById(int id) => _repository.getById(id);

  void add(Move move) {
    final created = _repository.add(move);
    _moves.add(created);
    notifyListeners();
  }

  void update(Move move) {
    _repository.update(move);
    final index = _moves.indexWhere((m) => m.id == move.id);
    if (index != -1) _moves[index] = move;
    notifyListeners();
  }

  void delete(int id) {
    _repository.delete(id);
    _moves.removeWhere((m) => m.id == id);
    notifyListeners();
  }

}
