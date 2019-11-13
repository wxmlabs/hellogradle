package com.wxmlabs.hellogradle


import com.alibaba.fastjson.JSONObject
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class EchoImplTest extends Specification {
    def "test echo"() {
        setup:
        def impl = new EchoImpl()

        expect:
        // equals
        output == impl.echo(input)

        where:
        input                || output
        'hello, world'       || 'hello, world\r\n'
        'hello, simon '      || 'hello, simon\r\n'
        ' hello, wxmlabs!\n' || 'hello, wxmlabs!\r\n'
    }

    def "test echo json command"() {
        setup:
        def impl = new EchoImpl()

        expect:
        def jsonCmd = new JSONObject([
                'cmd'    : 'echo',
                'content': input
        ])
        // equals
        output == impl.echoJsonCmd(jsonCmd)

        where:
        input                || output
        'hello, world'       || 'hello, world\r\n'
        'hello, simon '      || 'hello, simon\r\n'
        ' hello, wxmlabs!\n' || 'hello, wxmlabs!\r\n'
    }

    def "test invalid argument exception"() {
        setup:
        def impl = new EchoImpl()

        when:
        impl.echoJsonCmd(null)

        then:
        thrown(IllegalArgumentException)
    }
}
