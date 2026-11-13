import 'package:flutter/material.dart';

import '../../models/move.dart';

class MoveForm extends StatefulWidget {
  final Move? initial;
  final String submitLabel;
  final void Function(Move move) onSubmit;
  final VoidCallback onCancel;

  const MoveForm({
    super.key,
    this.initial,
    required this.submitLabel,
    required this.onSubmit,
    required this.onCancel,
  });

  @override
  State<MoveForm> createState() => _MoveFormState();
}

class _MoveFormState extends State<MoveForm> {
  final _formKey = GlobalKey<FormState>();
  late final TextEditingController _name;
  late final TextEditingController _category;
  late final TextEditingController _startingPosition;
  late final TextEditingController _lastAttemptText;
  late final TextEditingController _timesPracticed;
  late final TextEditingController _proficiency;
  late bool _attempted;
  DateTime? _lastAttempt;

  @override
  void initState() {
    super.initState();
    final initial = widget.initial;
    _name = TextEditingController(text: initial?.name ?? '');
    _category = TextEditingController(text: initial?.category ?? '');
    _startingPosition = TextEditingController(text: initial?.startingPosition ?? '');
    _attempted = initial?.attempted ?? false;
    _lastAttempt = initial?.lastAttempt;
    _lastAttemptText = TextEditingController(text: _formatDate(_lastAttempt));
    final times = initial?.timesPracticed ?? 0;
    _timesPracticed = TextEditingController(text: times > 0 ? '$times' : '');
    _proficiency = TextEditingController(text: initial?.proficiency?.toString() ?? '');
  }

  @override
  void dispose() {
    _name.dispose();
    _category.dispose();
    _startingPosition.dispose();
    _lastAttemptText.dispose();
    _timesPracticed.dispose();
    _proficiency.dispose();
    super.dispose();
  }

  static String _formatDate(DateTime? date) {
    if (date == null) return '';
    final month = date.month.toString().padLeft(2, '0');
    final day = date.day.toString().padLeft(2, '0');
    return '${date.year}-$month-$day';
  }

  String? _required(String? value, String field) {
    if (value == null || value.trim().isEmpty) return '$field is required';
    return null;
  }

  String? _validateTimesPracticed(String? value) {
    final text = value?.trim() ?? '';
    if (text.isEmpty) return null;
    final number = int.tryParse(text);
    if (number == null || number < 1) return 'Enter a whole number, at least 1';
    return null;
  }

  String? _validateProficiency(String? value) {
    final text = value?.trim() ?? '';
    if (text.isEmpty) return null;
    final number = int.tryParse(text);
    if (number == null || number < 1 || number > 10) return 'Enter a whole number from 1 to 10';
    return null;
  }

  String? _validateLastAttempt(String? _) {
    final date = _lastAttempt;
    if (date != null && date.isAfter(DateTime.now())) return "Date can't be in the future";
    return null;
  }

  Future<void> _pickDate() async {
    final now = DateTime.now();
    final picked = await showDatePicker(
      context: context,
      initialDate: _lastAttempt ?? now,
      firstDate: DateTime(2000),
      lastDate: now,
    );
    if (picked == null) return;
    setState(() {
      _lastAttempt = picked;
      _lastAttemptText.text = _formatDate(picked);
    });
  }

  void _clearDate() {
    setState(() {
      _lastAttempt = null;
      _lastAttemptText.text = '';
    });
  }

  void _submit() {
    if (!_formKey.currentState!.validate()) return;
    final startingPosition = _startingPosition.text.trim();
    widget.onSubmit(
      Move(
        id: widget.initial?.id ?? 0,
        name: _name.text.trim(),
        category: _category.text.trim(),
        startingPosition: startingPosition.isEmpty ? null : startingPosition,
        attempted: _attempted,
        lastAttempt: _attempted ? _lastAttempt : null,
        timesPracticed: _attempted ? int.tryParse(_timesPracticed.text.trim()) ?? 0 : 0,
        proficiency: _attempted ? int.tryParse(_proficiency.text.trim()) : null,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          TextFormField(
            controller: _name,
            decoration: const InputDecoration(labelText: 'Name'),
            validator: (value) => _required(value, 'Name'),
          ),
          TextFormField(
            controller: _category,
            decoration: const InputDecoration(labelText: 'Category'),
            validator: (value) => _required(value, 'Category'),
          ),
          TextFormField(
            controller: _startingPosition,
            decoration: const InputDecoration(labelText: 'Starting position'),
          ),
          const SizedBox(height: 8),
          SwitchListTile(
            contentPadding: EdgeInsets.zero,
            title: const Text('Attempted'),
            value: _attempted,
            onChanged: (value) => setState(() => _attempted = value),
          ),
          if (_attempted) ...[
            TextFormField(
              controller: _lastAttemptText,
              readOnly: true,
              onTap: _pickDate,
              decoration: InputDecoration(
                labelText: 'Last attempt',
                suffixIcon: _lastAttempt == null
                    ? const Icon(Icons.calendar_today)
                    : IconButton(icon: const Icon(Icons.clear), onPressed: _clearDate),
              ),
              validator: _validateLastAttempt,
            ),
            TextFormField(
              controller: _timesPracticed,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Times practiced'),
              validator: _validateTimesPracticed,
            ),
            TextFormField(
              controller: _proficiency,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(labelText: 'Proficiency', suffixText: '/10'),
              validator: _validateProficiency,
            ),
          ],
          const SizedBox(height: 24),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              TextButton(onPressed: widget.onCancel, child: const Text('Cancel')),
              const SizedBox(width: 8),
              FilledButton(onPressed: _submit, child: Text(widget.submitLabel)),
            ],
          ),
        ],
      ),
    );
  }
}
