class Move {
  final int id;
  final String name;
  final String category;
  final String? startingPosition;
  final bool attempted;
  final DateTime? lastAttempt;
  final int timesPracticed;
  final int? proficiency;

  const Move({
    required this.id,
    required this.name,
    required this.category,
    this.startingPosition,
    this.attempted = false,
    this.lastAttempt,
    this.timesPracticed = 0,
    this.proficiency,
  });

  Move copyWith({
    int? id,
    String? name,
    String? category,
    String? startingPosition,
    bool? attempted,
    DateTime? lastAttempt,
    int? timesPracticed,
    int? proficiency,
  }) {
    return Move(
      id: id ?? this.id,
      name: name ?? this.name,
      category: category ?? this.category,
      startingPosition: startingPosition ?? this.startingPosition,
      attempted: attempted ?? this.attempted,
      lastAttempt: lastAttempt ?? this.lastAttempt,
      timesPracticed: timesPracticed ?? this.timesPracticed,
      proficiency: proficiency ?? this.proficiency,
    );
  }
}
