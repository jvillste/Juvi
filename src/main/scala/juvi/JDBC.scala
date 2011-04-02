package juvi

import java.sql.{PreparedStatement, Statement}
import juvi.Using.using

trait JDBC extends ConnectionProvider
{

  def usingStatement[A](f: Statement => A): A =
  {
    using(getConnection) {connection =>
      using(connection.createStatement){statement =>
       // println(statement.toString)

        f(statement)
      }
    }
  }

  def usingPreparedStatement[A](statementSource:String)(f: PreparedStatement => A) : A =
  {
    // println(statementSource)

    using(getConnection) {connection =>
      using(connection.prepareStatement(statementSource)){preparedStatement =>
        f(preparedStatement)
      }
    }
  }

  def executeStatement(sql:String)
  {
    //println(sql)
    usingStatement { statement =>
      statement.execute(sql)
    }
  }
}
