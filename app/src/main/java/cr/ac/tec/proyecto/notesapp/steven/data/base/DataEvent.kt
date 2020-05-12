package cr.ac.tec.proyecto.notesapp.steven.data.base

class DataEvent<T> (
    var onSuccess: (Response<T>) -> Unit,
    var onError: (String) -> Unit
)