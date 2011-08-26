package developments

def builder = new groovy.json.JsonBuilder()
def root = builder(['asd','qwe'])

out << builder.toString()

return