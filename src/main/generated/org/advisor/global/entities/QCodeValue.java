package org.advisor.global.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeValue is a Querydsl query type for CodeValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeValue extends EntityPathBase<CodeValue> {

    private static final long serialVersionUID = 1568529514L;

    public static final QCodeValue codeValue = new QCodeValue("codeValue");

    public final StringPath code = createString("code");

    public final StringPath value = createString("value");

    public QCodeValue(String variable) {
        super(CodeValue.class, forVariable(variable));
    }

    public QCodeValue(Path<? extends CodeValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeValue(PathMetadata metadata) {
        super(CodeValue.class, metadata);
    }

}

