import '../models/move.dart';

abstract class MoveRepository {
  List<Move> getAll();
  Move? getById(int id);
  Move add(Move move);
  void update(Move move);
  void delete(int id);
}

class InMemoryMoveRepository implements MoveRepository {
  final List<Move> _moves = [
    Move(id: 1, name: 'RNC', category: 'Submission', startingPosition: 'opponent back', attempted: true, lastAttempt: DateTime(2026, 6, 29), timesPracticed: 100, proficiency: 4),
    Move(id: 2, name: 'Armbar', category: 'Submission', startingPosition: 'Mount or closed guard', attempted: true, lastAttempt: DateTime(2026, 6, 20), timesPracticed: 85, proficiency: 3),
    Move(id: 3, name: 'Triangle Choke', category: 'Submission', startingPosition: 'Closed guard', attempted: true, lastAttempt: DateTime(2026, 6, 18), timesPracticed: 90, proficiency: 4),
    Move(id: 4, name: 'Kimura', category: 'Submission', startingPosition: 'Side control or guard', attempted: true, lastAttempt: DateTime(2026, 6, 15), timesPracticed: 75, proficiency: 3),
    const Move(id: 5, name: 'Guillotine', category: 'Submission', startingPosition: 'Front headlock'),
    Move(id: 6, name: 'Americana', category: 'Submission', startingPosition: 'Side control', attempted: true, lastAttempt: DateTime(2026, 6, 10), timesPracticed: 65, proficiency: 2),
    Move(id: 7, name: 'Double Leg Takedown', category: 'Takedown', startingPosition: 'Standing', attempted: true, lastAttempt: DateTime(2026, 6, 8), timesPracticed: 15, proficiency: 3),
    Move(id: 8, name: 'Hip Escape', category: 'Escape', startingPosition: 'Side control bottom', attempted: true, lastAttempt: DateTime(2026, 6, 5), timesPracticed: 20, proficiency: 4),
  ];

  int _nextId = 9;

  @override
  List<Move> getAll() => List.unmodifiable(_moves);

  @override
  Move? getById(int id) {
    for (final move in _moves) {
      if (move.id == id) return move;
    }
    return null;
  }

  @override
  Move add(Move move) {
    final created = move.copyWith(id: _nextId++);
    _moves.add(created);
    return created;
  }

  @override
  void update(Move move) {
    final index = _moves.indexWhere((m) => m.id == move.id);
    if (index != -1) _moves[index] = move;
  }

  @override
  void delete(int id) {
    _moves.removeWhere((m) => m.id == id);
  }
}
