package io.bastioncore.core.resolvers.impl

import io.bastioncore.core.resolvers.IResourceResolver
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope('singleton')
class FileSystemResolver implements IResourceResolver {

    @Override
    def getResource(LinkedHashMap params) {
        String path = 'etc/'
        switch(params.type){
            case 'scripts':
                path+='scripts/'
                break
            case 'layout':
                path+='layouts/'
                break
        }
        File file = new File(path+params.name)
        FileReader reader = new FileReader(file)
        String item = reader.getText()
        reader.close()
        return item
    }
}
